package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.auth.RefreshTokenExpiredException
import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.auth.fixture.refreshTokenFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.port.UserRefreshTokenIdListPersistencePort
import com.ttasjwi.board.system.user.domain.port.RefreshTokenIdPersistencePort
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("RefreshTokenHandler: 리프레시토큰 처리기")
class RefreshTokenHandlerTest {

    private lateinit var refreshTokenHandler: RefreshTokenHandler
    private lateinit var refreshTokenGeneratePort: RefreshTokenGeneratePort
    private lateinit var refreshTokenParsePort: RefreshTokenParsePort
    private lateinit var userRefreshTokenIdListPersistencePort: UserRefreshTokenIdListPersistencePort
    private lateinit var refreshTokenIdPersistencePort: RefreshTokenIdPersistencePort

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        refreshTokenHandler = container.refreshTokenHandler
        refreshTokenGeneratePort = container.refreshTokenPortFixture
        refreshTokenParsePort = container.refreshTokenPortFixture
        userRefreshTokenIdListPersistencePort = container.userRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePort = container.refreshTokenIdPersistencePortFixture
    }

    @Nested
    @DisplayName("createAndPersist: 토큰 생성 및 저장")
    inner class CreateAndPersistTest {

        @Test
        @DisplayName("생성 후 저장된다.")
        fun simpleSuccess() {
            // given
            val userId = 1L
            val issuedAt = appDateTimeFixture(dayOfMonth = 1)

            // when
            val refreshToken = refreshTokenHandler.createAndPersist(userId, issuedAt)

            // then
            val userRefreshTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)
            val createdTokenIsSaved = refreshTokenIdPersistencePort.exists(userId, refreshToken.refreshTokenId)

            assertThat(refreshToken.userId).isEqualTo(userId)
            assertThat(refreshToken.refreshTokenId).isNotNull()
            assertThat(refreshToken.tokenValue).isNotNull()
            assertThat(userRefreshTokenIds).containsExactly(refreshToken.refreshTokenId)
            assertThat(createdTokenIsSaved).isTrue()
        }

        @Test
        @DisplayName("생성 후 저장과정하기 직전, 실제 만료된 리프레시토큰Id 들을 찾아 회원리프레시토큰Id 목록에서 제거 후 저장한다.")
        fun actuallyExpiredTokenRemoveFromUserRefreshTokenIdListTest() {
            // given
            val userId = 2L

            userRefreshTokenIdListPersistencePort.add(userId, 1L, limit = 3)

            userRefreshTokenIdListPersistencePort.add(userId, 2L, limit = 2)
            refreshTokenIdPersistencePort.save(userId, 2L, expiresAt = appDateTimeFixture(dayOfMonth = 2, hour = 1))

            val issuedAt = appDateTimeFixture(dayOfMonth = 1, hour = 2)

            // when
            val refreshToken = refreshTokenHandler.createAndPersist(userId, issuedAt)

            // then
            val userRefreshTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)

            assertThat(refreshToken.userId).isEqualTo(userId)
            assertThat(refreshToken.refreshTokenId).isNotNull()
            assertThat(refreshToken.tokenValue).isNotNull()
            assertThat(userRefreshTokenIds).containsExactlyInAnyOrder(2L, refreshToken.refreshTokenId)
            assertThat(refreshTokenIdPersistencePort.exists(userId, 1L)).isFalse()
            assertThat(refreshTokenIdPersistencePort.exists(userId, 2L)).isTrue()
            assertThat(refreshTokenIdPersistencePort.exists(userId, refreshToken.refreshTokenId)).isTrue()
        }

        /**
         * 위의 과정을 거쳤음에도 삭제된 토큰이 없고 토큰 갯수 제한에 걸리면
         * 가장 오래된 토큰이 제거되어야함(최대 5개 유지, 회원 리프레시토큰목록 및 리프레시토큰 저장소 양쪽 확인)
         * 그 후 토큰을 회원 토큰 목록, 토큰 저장소에 새로 추가함.
         *
         */
        @Test
        @DisplayName("회원의 토큰목록이 가득찼고 모두 유효하다면, 가장 오래된 토큰을 제거하고 새로운 토큰을 추가한다.")
        fun ifTokenCountIsMaximumTest1() {
            // given
            val userId = 1L
            val saveCount = RefreshTokenHandler.MAX_USER_REFRESH_TOKEN_COUNT + 1

            // when
            val savedTokenIds = mutableListOf<Long>()
            for (i in 1..saveCount) {
                val token = refreshTokenHandler.createAndPersist(
                    userId = 1L,
                    issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = i)
                )
                savedTokenIds.add(token.refreshTokenId)
            }

            // then
            val userRefreshTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)

            assertThat(refreshTokenIdPersistencePort.exists(userId, savedTokenIds.first())).isFalse()

            savedTokenIds.removeFirst()
            assertThat(userRefreshTokenIds).containsExactlyInAnyOrderElementsOf(savedTokenIds)
        }


        @Test
        @DisplayName("회원의 토큰목록이 가득찼으나 존재하지 않는 토큰이 하나라도 있는 경우, 가장 오래된 토큰은 제거되지 않는다.")
        fun ifTokenCountIsMaximumTest2() {
            // given
            val userId = 1L

            // when
            val firstToken = refreshTokenHandler.createAndPersist(
                userId = 1,
                issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = 1)
            )

            var currentTokenId = firstToken.refreshTokenId
            for (i in 2..RefreshTokenHandler.MAX_USER_REFRESH_TOKEN_COUNT) {
                currentTokenId += 1L
                userRefreshTokenIdListPersistencePort.add(
                    userId,
                    refreshTokenId = currentTokenId,
                    limit = RefreshTokenHandler.MAX_USER_REFRESH_TOKEN_COUNT.toLong()
                )
            }

            val lastToken = refreshTokenHandler.createAndPersist(
                userId = 1,
                issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = 20)
            )

            // then
            val userRefreshTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)
            assertThat(refreshTokenIdPersistencePort.exists(userId, firstToken.refreshTokenId)).isTrue()
            assertThat(refreshTokenIdPersistencePort.exists(userId, lastToken.refreshTokenId)).isTrue()
            assertThat(userRefreshTokenIds).containsExactlyInAnyOrder(
                firstToken.refreshTokenId,
                lastToken.refreshTokenId
            )
        }
    }


    @Nested
    @DisplayName("parseTest: 토큰 파싱")
    inner class ParseTest {

        @Test
        @DisplayName("파싱하면 원본 리프레시토큰 값을 복원한다.")
        fun test() {
            // given
            val refreshToken = refreshTokenGeneratePort.generate(
                1L,
                2L,
                appDateTimeFixture(dayOfMonth = 1),
                appDateTimeFixture(dayOfMonth = 2)
            )

            // when
            val parsedToken = refreshTokenHandler.parse(refreshToken.tokenValue)

            // then
            assertThat(parsedToken.userId).isEqualTo(refreshToken.userId)
            assertThat(parsedToken.refreshTokenId).isEqualTo(refreshToken.refreshTokenId)
            assertThat(parsedToken.issuedAt).isEqualTo(refreshToken.issuedAt)
            assertThat(parsedToken.expiresAt).isEqualTo(refreshToken.expiresAt)
            assertThat(parsedToken.tokenValue).isEqualTo(refreshToken.tokenValue)
        }
    }

    @Nested
    @DisplayName("throwIfExpired: 토큰이 유효기간이 지났거나, 저장소에 존재하지 않을 경우 예외 발생")
    inner class ThrowIfExpiredTest {


        @Test
        @DisplayName("리프레시토큰이 현재 유효하고, 리프레시토큰아이디 저장소에 있는 경우, 예외 발생 안 함.")
        fun test1() {
            // given
            val refreshToken = refreshTokenHandler.createAndPersist(
                userId = 1L,
                issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = 0)
            )

            // when
            assertDoesNotThrow {
                refreshTokenHandler.throwIfExpired(
                    refreshToken,
                    currentTime = refreshToken.expiresAt.minusSeconds(1)
                )
            }
        }


        @Test
        @DisplayName("토큰의 유효기간이 지난 경우 예외 발생")
        fun test2() {
            // given
            val refreshToken =
                refreshTokenFixture(userId = 1L, refreshTokenId = 1L, expiresAt = appDateTimeFixture(dayOfMonth = 2))

            //when
            val ex = assertThrows<RefreshTokenExpiredException> {
                refreshTokenHandler.throwIfExpired(
                    refreshToken,
                    currentTime = appDateTimeFixture(dayOfMonth = 2, second = 1)
                )
            }
            assertThat(ex.debugMessage).isEqualTo("리프레시 토큰 만료시간이 경과되어 만료됨")
        }


        @Test
        @DisplayName("토큰의 유효기간은 유효하지만, 리프레시토큰 목록에 존재하지 않는 경우 예외 발생")
        fun test3() {
            // given
            val refreshToken =
                refreshTokenFixture(userId = 1L, refreshTokenId = 1L, expiresAt = appDateTimeFixture(dayOfMonth = 2))

            // when
            // then
            val ex = assertThrows<RefreshTokenExpiredException> {
                refreshTokenHandler.throwIfExpired(
                    refreshToken,
                    currentTime = refreshToken.expiresAt.minusSeconds(1)
                )
            }

            assertThat(ex.debugMessage).isEqualTo("리프레시 토큰이 로그아웃, 재갱신, 동시 토큰제약 등의 이유로 만료됨")
        }

    }


    @Nested
    @DisplayName("refreshIfRequired : 리프레시토큰이 갱신이 필요하다면, 갱신해서 반환한다.")
    inner class RefreshIfRequired {

        /**
         * 리프레시 갱신선을 넘은경우 갱신
         */

        @Test
        @DisplayName("리프레시 토큰 재갱신이 필요할 경우 갱신하고, 기존 토큰을 말소한다.")
        fun test1() {
            // given
            val userId = 1L
            val prevRefreshToken = refreshTokenHandler.createAndPersist(
                userId = userId,
                issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = 0)
            )

            Thread.sleep(10)

            // when
            val newRefreshToken = refreshTokenHandler.refreshIfRequired(
                refreshToken = prevRefreshToken,
                currentTime = prevRefreshToken.expiresAt.minusHours(
                    RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS
                ).plusSeconds(1)
            )

            // then
            val userTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)
            assertThat(newRefreshToken.userId).isEqualTo(prevRefreshToken.userId)
            assertThat(newRefreshToken.refreshTokenId).isNotEqualTo(prevRefreshToken.refreshTokenId)
            assertThat(newRefreshToken.tokenValue).isNotEqualTo(prevRefreshToken.tokenValue)
            assertThat(newRefreshToken.issuedAt).isNotEqualTo(prevRefreshToken.issuedAt)
            assertThat(newRefreshToken.expiresAt).isNotEqualTo(prevRefreshToken.expiresAt)

            assertThat(userTokenIds).containsExactly(newRefreshToken.refreshTokenId)
            assertThat(refreshTokenIdPersistencePort.exists(userId, prevRefreshToken.refreshTokenId)).isFalse()
            assertThat(refreshTokenIdPersistencePort.exists(userId, newRefreshToken.refreshTokenId)).isTrue()
        }

        /**
         * 리프레시 갱신선을 넘지 않은 경우 토큰 그대로 반환
         */
        @Test
        @DisplayName("리프레시 토큰 재갱신이 필요하지 않을 경우 갱신하지 않고 그대로 반환한다.")
        fun test2() {
            // given
            val userId = 1L
            val prevRefreshToken = refreshTokenHandler.createAndPersist(
                userId = userId,
                issuedAt = appDateTimeFixture(dayOfMonth = 1, minute = 0)
            )

            // when
            val newRefreshToken = refreshTokenHandler.refreshIfRequired(
                refreshToken = prevRefreshToken,
                currentTime = prevRefreshToken.expiresAt.minusHours(
                    RefreshTokenHandler.REFRESH_REQUIRE_THRESHOLD_HOURS
                ).minusSeconds(1)
            )

            // then
            val userTokenIds = userRefreshTokenIdListPersistencePort.findAll(userId)
            assertThat(newRefreshToken.userId).isEqualTo(prevRefreshToken.userId)
            assertThat(newRefreshToken.refreshTokenId).isEqualTo(prevRefreshToken.refreshTokenId)
            assertThat(newRefreshToken.tokenValue).isEqualTo(prevRefreshToken.tokenValue)
            assertThat(newRefreshToken.issuedAt).isEqualTo(prevRefreshToken.issuedAt)
            assertThat(newRefreshToken.expiresAt).isEqualTo(prevRefreshToken.expiresAt)

            assertThat(userTokenIds).containsExactly(prevRefreshToken.refreshTokenId)
            assertThat(refreshTokenIdPersistencePort.exists(userId, prevRefreshToken.refreshTokenId)).isTrue()
        }
    }
}
