package com.ttasjwi.board.system.domain.auth.service.impl

import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.auth.exception.AccessTokenExpiredException
import com.ttasjwi.board.system.domain.auth.external.fixture.ExternalAccessTokenManagerFixture
import com.ttasjwi.board.system.domain.auth.model.AccessToken
import com.ttasjwi.board.system.domain.auth.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.domain.auth.service.AccessTokenManager
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
            val issuedAt = appDateTimeFixture(minute = 5)

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
            val issuedAt = appDateTimeFixture(minute = 5)
            val tokenValue = accessTokenManager.generate(authMember, issuedAt).tokenValue

            // when
            val accessToken = accessTokenManager.parse(tokenValue)

            // then
            assertThat(accessToken.authMember).isEqualTo(authMemberFixture())
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(appDateTimeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(appDateTimeFixture(minute = 5).plusMinutes(AccessToken.VALIDITY_MINUTE))
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
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 2)
            )
            val currentTime = appDateTimeFixture(minute = 1)

            // when
            // then
            accessTokenManager.checkCurrentlyValid(accessToken, currentTime)
        }

        @Test
        @DisplayName("현재 시간이 만료시간과 같음 -> 예외")
        fun testExpired1() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 2)
            )
            val currentTime = appDateTimeFixture(minute = 2)

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
                issuedAt = appDateTimeFixture(minute = 0),
                expiresAt = appDateTimeFixture(minute = 2)
            )
            val currentTime = appDateTimeFixture(minute = 3)

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
