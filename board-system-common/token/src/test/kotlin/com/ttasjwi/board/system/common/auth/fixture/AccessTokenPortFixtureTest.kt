package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AccessTokenManagerFixture: 액세스토큰 생성, 파싱 기능 모두 담당하는 픽스쳐")
class AccessTokenPortFixtureTest {

    private lateinit var accessTokenPortFixture: AccessTokenPortFixture

    @BeforeEach
    fun setup() {
        accessTokenPortFixture = AccessTokenPortFixture()
    }

    @Nested
    @DisplayName("Generate : 액세스토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val authMember = authUserFixture()
            val issuedAt = appDateTimeFixture(minute = 5)
            val expiresAt = appDateTimeFixture(minute = 35)

            // when
            val accessToken = accessTokenPortFixture.generate(authMember, issuedAt, expiresAt)

            println("AccessToken Value: ${accessToken.tokenValue}")

            // then
            assertThat(accessToken.authUser).isEqualTo(authMember)
            assertThat(accessToken.tokenType).isEqualTo(AccessToken.VALID_TOKEN_TYPE)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
            assertThat(accessToken.issuer).isEqualTo(AccessToken.VALID_ISSUER)
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
            val authMember = authUserFixture()
            val issuedAt = appDateTimeFixture(minute = 5)
            val expiresAt = appDateTimeFixture(minute = 35)
            val tokenValue = accessTokenPortFixture.generate(authMember, issuedAt, expiresAt).tokenValue

            // when
            val accessToken = accessTokenPortFixture.parse(tokenValue)

            // then
            assertThat(accessToken.authUser).isEqualTo(authUserFixture())
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(appDateTimeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(appDateTimeFixture(minute = 35))
        }
    }
}
