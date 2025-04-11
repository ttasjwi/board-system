package com.ttasjwi.board.system.common.websupport.auth.token

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.websupport.auth.exception.InvalidAccessTokenFormatException
import com.ttasjwi.board.system.common.websupport.auth.token.JwtAccessTokenParser.Companion.TOKEN_TYPE_CLAIM
import com.ttasjwi.board.system.common.websupport.test.WebSupportIntegrationTest
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

class JwtAccessTokenGeneratorAndParserTest : WebSupportIntegrationTest() {

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
            val accessToken = accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.tokenValue).isNotNull()
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 인증된 회원의 정보를 가진다.")
        fun test2() {
            val accessToken = accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.authMember.memberId).isEqualTo(authMember.memberId)
            assertThat(accessToken.authMember.role).isEqualTo(authMember.role)
        }

        @Test
        @DisplayName("생성된 토큰 객체는 전달받은 발급시간 정보를 가진다")
        fun test3() {
            val accessToken = accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 만료시간 정보를 가진다.")
        fun test4() {
            val accessToken = accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 토큰값을 기반으로 액세스 토큰 객체를 복원한다.")
    inner class Parse {

        @Test
        @DisplayName("복원된 토큰 객체는 처음 생성시점의 액세스 토큰과 동등하다.")
        fun test1() {
            val accessToken = accessTokenGenerator.generate(authMember, issuedAt, expiresAt)
            val tokenValue = accessToken.tokenValue
            val parsedAccessToken = accessTokenParser.parse(tokenValue)

            println("tokenValue = $tokenValue")
            println("authMember = ${parsedAccessToken.authMember}")
            assertThat(parsedAccessToken).isEqualTo(accessToken)
        }

        @Test
        @DisplayName("포맷이 잘못됐을 경우, 예외가 발생한다.")
        fun test2() {
            val tokenValue = "Adfadfadfadf"

            val exception =
                assertThrows<InvalidAccessTokenFormatException> { accessTokenParser.parse(tokenValue) }
            assertThat(exception.cause).isInstanceOf(JwtException::class.java)
        }

        @Test
        @DisplayName("토큰타입이 '${AccessToken.VALID_TOKEN_TYPE}'이 아닐 경우 예외가 발생한다.")
        fun test3() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val accessTokenParser = JwtAccessTokenParser(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(TOKEN_TYPE_CLAIM) } returns "RefreshToken"
            every { jwt.claims["iss"] } returns AccessToken.VALID_ISSUER

            assertThrows<InvalidAccessTokenFormatException> { accessTokenParser.parse(tokenValue) }
        }

        @Test
        @DisplayName("토큰 발급자가 '${AccessToken.VALID_ISSUER}'이 아닐 경우 예외가 발생한다.")
        fun test4() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val accessTokenParser = JwtAccessTokenParser(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(TOKEN_TYPE_CLAIM) } returns AccessToken.VALID_ISSUER
            every { jwt.claims["iss"] } returns "invalidIssuser"

            assertThrows<InvalidAccessTokenFormatException> { accessTokenParser.parse(tokenValue) }
        }
    }
}
