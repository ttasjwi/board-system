package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.exception.RefreshTokenExpiredException
import com.ttasjwi.board.system.auth.domain.external.fixture.ExternalRefreshTokenManagerFixture
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("RefreshTokenManagerImpl 테스트")
class RefreshTokenManagerImplTest {

    private lateinit var refreshTokenManager: RefreshTokenManager

    @BeforeEach
    fun setup() {
        refreshTokenManager = RefreshTokenManagerImpl(
            externalRefreshTokenManager = ExternalRefreshTokenManagerFixture()
        )
    }

    companion object {
        private val log = getLogger(RefreshTokenManagerImplTest::class.java)
    }

    @Nested
    @DisplayName("Generate : 액세스토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val memberId = 144L
            val issuedAt = appDateTimeFixture(minute = 3)

            // when
            val refreshToken = refreshTokenManager.generate(memberId, issuedAt)

            log.info { "RefreshToken Value: ${refreshToken.tokenValue}" }

            // then
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isNotNull
            assertThat(refreshToken.refreshTokenId.value.length).isEqualTo(RefreshTokenId.REFRESH_TOKEN_ID_LENGTH)
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(issuedAt.plusHours(RefreshToken.VALIDITY_HOURS))
            assertThat(refreshToken.tokenValue).isNotNull()
        }
    }

    @Nested
    @DisplayName("parse: 문자열로부터 액세스 토큰을 파싱해, 액세스토큰 인스턴스를 생성한다.")
    inner class Parse {

        @Test
        @DisplayName("복원 성공 테스트")
        fun testSuccess() {
            // given
            val tokenValue =
                "144,abcdef,2025-01-01T00:03+09:00[Asia/Seoul],2025-01-02T00:03+09:00[Asia/Seoul],refreshToken"
            // when
            val accessToken = refreshTokenManager.parse(tokenValue)

            // then
            assertThat(accessToken.memberId).isEqualTo(144L)
            assertThat(accessToken.refreshTokenId.value).isEqualTo("abcdef")
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(appDateTimeFixture(minute = 3))
            assertThat(accessToken.expiresAt).isEqualTo(appDateTimeFixture(dayOfMonth = 2, minute = 3))
        }
    }

    @Nested
    @DisplayName("checkCurrentlyValid: 리프레시토큰이 현재 유효한 지 검증")
    inner class CheckCurrentlyValid {

        @Test
        @DisplayName("토큰이 만료되지 않았고, 리프레시토큰 홀더에 저장되어 있으면 유효하다")
        fun test1() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val refreshTokenHolder = refreshTokenHolderFixture(
                memberId = 123L,
                tokens = mutableMapOf(refreshTokenIdFixture("abc") to refreshToken)
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 1, minute = 5)

            // when
            // then
            refreshTokenManager.checkCurrentlyValid(refreshToken, refreshTokenHolder, currentTime)
        }

        @Test
        @DisplayName("토큰이 만료됐다면 예외가 발생한다.")
        fun test2() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val refreshTokenHolder = refreshTokenHolderFixture(
                memberId = 123L,
                tokens = mutableMapOf(refreshTokenIdFixture("abc") to refreshToken)
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 2, minute = 10)

            // when
            val ex = assertThrows<RefreshTokenExpiredException> {
                refreshTokenManager.checkCurrentlyValid(
                    refreshToken,
                    refreshTokenHolder,
                    currentTime
                )
            }
            // then
            assertThat(ex.debugMessage).isEqualTo("리프레시토큰 유효시간이 경과되어 만료됨(currentTime=${currentTime},expiresAt=${refreshToken.expiresAt})")
        }

        @Test
        @DisplayName("토큰이 현 시간 기준, 유효시간 내지만 토큰이 리프레시토큰 홀더에 없을 경우, 무효화된 것으로 간주하여 예외가 발생한다.")
        fun test3() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val refreshTokenHolder = refreshTokenHolderFixture(
                memberId = 123L,
                tokens = mutableMapOf()
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 1, minute = 10)

            // when
            val ex = assertThrows<RefreshTokenExpiredException> {
                refreshTokenManager.checkCurrentlyValid(
                    refreshToken,
                    refreshTokenHolder,
                    currentTime
                )
            }
            // then
            assertThat(ex.debugMessage).isEqualTo("리프레시 토큰이 로그아웃 또는 동시토큰 제한 등의 이유로 토큰이 만료됨. (memberId=${refreshToken.memberId},refreshTokenId=${refreshToken.refreshTokenId.value})")
        }
    }

    @Nested
    @DisplayName("isRefreshRequired: 리프레시토큰이 재갱신이 필요한 지 여부를 확인한다")
    inner class IsRefreshRequired {

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 ${RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전보다 이전 시간이면 재생신 필요가 없다.")
        fun testRefreshNotRequired() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS)
                .minusSeconds(1)

            // when
            val isRefreshRequired = refreshTokenManager.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isFalse()
        }

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 정확히 ${RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전이면 재생신해야한다.")
        fun testRequired1() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS)

            // when
            val isRefreshRequired = refreshTokenManager.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isTrue()
        }

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 ${RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전보다 이후면 재생신해야한다.")
        fun testRequired2() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = appDateTimeFixture(dayOfMonth = 1),
                expiresAt = appDateTimeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshToken.REFRESH_REQUIRE_THRESHOLD_HOURS)
                .plusSeconds(1)

            // when
            val isRefreshRequired = refreshTokenManager.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isTrue()
        }
    }
}
