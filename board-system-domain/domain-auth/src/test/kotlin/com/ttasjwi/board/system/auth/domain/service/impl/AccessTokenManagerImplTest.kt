package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.exception.AccessTokenExpiredException
import com.ttasjwi.board.system.auth.domain.external.fixture.ExternalAccessTokenManagerFixture
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.service.AccessTokenManager
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("AccessTokenManagerImpl 테스트")
class AccessTokenManagerImplTest {

    private lateinit var accessTokenManager: AccessTokenManager

    @BeforeEach
    fun setup() {
        accessTokenManager = AccessTokenManagerImpl(
            ExternalAccessTokenManagerFixture()
        )
    }

    companion object {
        private val log = getLogger(AccessTokenManagerImplTest::class.java)
    }


    @Nested
    @DisplayName("Generate : 액세스토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val authMember = authMemberFixture()
            val issuedAt = timeFixture(minute = 5)

            // when
            val accessToken = accessTokenManager.generate(authMember, issuedAt)

            log.info { "AccessToken Value: ${accessToken.tokenValue}" }

            // then
            assertThat(accessToken.authMember).isEqualTo(authMember)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(issuedAt.plusMinutes(AccessToken.VALIDITY_MINUTE))
            assertThat(accessToken.tokenValue).isNotNull()
        }
    }

    @Nested
    @DisplayName("parse: 문자열로부터 액세스 토큰을 파싱해, 액세스토큰 인스턴스를 생성한다.")
    inner class Parse {

        @Test
        @DisplayName("복원 성공 테스트")
        fun testSuccess() {
            // given
            val authMember = authMemberFixture()
            val issuedAt = timeFixture(minute = 5)
            val tokenValue = accessTokenManager.generate(authMember, issuedAt).tokenValue

            // when
            val accessToken = accessTokenManager.parse(tokenValue)

            // then
            assertThat(accessToken.authMember).isEqualTo(authMemberFixture())
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(minute = 5).plusMinutes(AccessToken.VALIDITY_MINUTE))
        }
    }

    @Nested
    @DisplayName("checkCurrentlyValid: 액세스토큰이 현재 유효한 지 검증한다")
    inner class CheckCurrentlyValid {


        @Test
        @DisplayName("현재 시간이 만료시간 이전 -> 성공")
        fun testValid() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = timeFixture(minute = 0),
                expiresAt = timeFixture(minute = 2)
            )
            val currentTime = timeFixture(minute = 1)

            // when
            // then
            accessTokenManager.checkCurrentlyValid(accessToken, currentTime)
        }

        @Test
        @DisplayName("현재 시간이 만료시간과 같음 -> 예외")
        fun testExpired1() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = timeFixture(minute = 0),
                expiresAt = timeFixture(minute = 2)
            )
            val currentTime = timeFixture(minute = 2)

            // when
            // then
            assertThrows<AccessTokenExpiredException> {
                accessTokenManager.checkCurrentlyValid(accessToken, currentTime)
            }
        }

        @Test
        @DisplayName("현재 시간이 만료시간 이후 -> 예외")
        fun testExpired2() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = timeFixture(minute = 0),
                expiresAt = timeFixture(minute = 2)
            )
            val currentTime = timeFixture(minute = 3)

            // when
            // then
            assertThrows<AccessTokenExpiredException> {
                accessTokenManager.checkCurrentlyValid(
                    accessToken,
                    currentTime
                )
            }
        }
    }
}
