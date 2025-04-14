package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.auth.infra.test.JwtIntegrationTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException

@DisplayName("Jwt 액세스 토큰 생성, 파싱 테스트")
class JwtAccessTokenPortTest : JwtIntegrationTest() {

    private val authMember = authMemberFixture(
        memberId = 1L,
        role = Role.USER
    )
    private val issuedAt = appDateTimeFixture(minute = 0)
    private val expiresAt = appDateTimeFixture(minute = 30)

    @Nested
    @DisplayName("generate: 로그인 한 회원의 인증정보를 담은 액세스 토큰 인스턴스를 생성한다.")
    inner class GenerateTest {

        @Test
        @DisplayName("생성된 토큰 인스턴스는 토큰값을 가진다.")
        fun test() {
            val accessToken = accessTokenGeneratePort.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.tokenValue).isNotNull()
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 인증된 회원의 정보를 가진다.")
        fun test2() {
            val accessToken = accessTokenGeneratePort.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.authMember.memberId).isEqualTo(authMember.memberId)
            assertThat(accessToken.authMember.role).isEqualTo(authMember.role)
        }

        @Test
        @DisplayName("생성된 토큰 객체는 전달받은 발급시간, 만료시간 정보를 가진다")
        fun test3() {
            val accessToken = accessTokenGeneratePort.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 토큰값을 기반으로 액세스 토큰 객체를 복원한다.")
    inner class Parse {

        @Test
        @DisplayName("복원된 토큰 객체는 처음 생성시점의 액세스 토큰의 상태를 유지한다.")
        fun test1() {
            val accessToken = accessTokenGeneratePort.generate(authMember, issuedAt, expiresAt)
            val parsedAccessToken = accessTokenParsePort.parse(accessToken.tokenValue)

            println("tokenValue = ${accessToken.tokenValue}")
            println("authMember = ${parsedAccessToken.authMember}")

            assertThat(parsedAccessToken.authMember).isEqualTo(accessToken.authMember)
            assertThat(parsedAccessToken.tokenType).isEqualTo(accessToken.tokenType)
            assertThat(parsedAccessToken.tokenValue).isEqualTo(accessToken.tokenValue)
            assertThat(parsedAccessToken.issuer).isEqualTo(accessToken.issuer)
            assertThat(parsedAccessToken.issuedAt).isEqualTo(accessToken.issuedAt)
            assertThat(parsedAccessToken.expiresAt).isEqualTo(accessToken.expiresAt)
        }

        @Test
        @DisplayName("포맷이 잘못됐을 경우, 예외가 발생한다.")
        fun test2() {
            val tokenValue = "Adfadfadfadf"

            val exception =
                assertThrows<InvalidAccessTokenFormatException> { accessTokenParsePort.parse(tokenValue) }
            assertThat(exception.cause).isInstanceOf(JwtException::class.java)
        }

        @Test
        @DisplayName("토큰타입이 '${AccessToken.VALID_TOKEN_TYPE}'이 아닐 경우 예외가 발생한다.")
        fun test3() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val accessTokenParsePort = JwtAccessTokenParseAdapter(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(JwtAccessTokenParseAdapter.TOKEN_TYPE_CLAIM) } returns "RefreshToken"
            every { jwt.claims["iss"] } returns AccessToken.VALID_ISSUER

            assertThrows<InvalidAccessTokenFormatException> { accessTokenParsePort.parse(tokenValue) }
        }

        @Test
        @DisplayName("토큰 발급자가 '${AccessToken.VALID_ISSUER}'이 아닐 경우 예외가 발생한다.")
        fun test4() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val accessTokenParsePort = JwtAccessTokenParseAdapter(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(JwtAccessTokenParseAdapter.TOKEN_TYPE_CLAIM) } returns AccessToken.VALID_ISSUER
            every { jwt.claims["iss"] } returns "invalidIssuser"

            assertThrows<InvalidAccessTokenFormatException> { accessTokenParsePort.parse(tokenValue) }
        }
    }
}
