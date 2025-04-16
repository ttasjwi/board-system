package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.auth.RefreshToken
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

@DisplayName("Jwt 리프레시토큰 생성, 파싱 테스트")
class JwtRefreshTokenPortTest : JwtIntegrationTest() {

    private val userId = 12345678L
    private val refreshTokenId = 12431541666L
    private val issuedAt = appDateTimeFixture(dayOfMonth = 1)
    private val expiresAt = appDateTimeFixture(dayOfMonth = 2)

    @Nested
    @DisplayName("generate: 리프레시 토큰 인스턴스를 생성한다.")
    inner class GenerateTest {

        @Test
        @DisplayName("생성된 리프레시 토큰 인스턴스는 토큰값을 가진다.")
        fun test() {
            val refreshToken = refreshTokenGeneratePort.generate(userId, refreshTokenId, issuedAt, expiresAt)
            assertThat(refreshToken.tokenValue).isNotNull()
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 식별정보를 가진다.")
        fun test2() {
            val refreshToken = refreshTokenGeneratePort.generate(userId, refreshTokenId, issuedAt, expiresAt)
            assertThat(refreshToken.userId).isEqualTo(userId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
        }

        @Test
        @DisplayName("생성된 토큰 객체는 전달받은 발급시간 정보, 만료시간 정보를 가진다")
        fun test3() {
            val refreshToken = refreshTokenGeneratePort.generate(userId, refreshTokenId, issuedAt, expiresAt)
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 토큰값을 기반으로 액세스 토큰 객체를 복원한다.")
    inner class Parse {

        @Test
        @DisplayName("복원된 토큰 객체는 처음 생성시점의 액세스 토큰과 동등하다.")
        fun test1() {
            val refreshToken = refreshTokenGeneratePort.generate(userId, refreshTokenId, issuedAt, expiresAt)
            val tokenValue = refreshToken.tokenValue
            val parsedRefreshToken = refreshTokenParsePort.parse(tokenValue)

            println("tokenValue = $tokenValue")
            println("userId = ${parsedRefreshToken.userId}")
            println("refreshTokenId = ${parsedRefreshToken.refreshTokenId}")

            assertThat(parsedRefreshToken.userId).isEqualTo(refreshToken.userId)
            assertThat(parsedRefreshToken.refreshTokenId).isEqualTo(refreshToken.refreshTokenId)
            assertThat(parsedRefreshToken.tokenType).isEqualTo(refreshToken.tokenType)
            assertThat(parsedRefreshToken.tokenValue).isEqualTo(refreshToken.tokenValue)
            assertThat(parsedRefreshToken.issuer).isEqualTo(refreshToken.issuer)
            assertThat(parsedRefreshToken.issuedAt).isEqualTo(refreshToken.issuedAt)
            assertThat(parsedRefreshToken.expiresAt).isEqualTo(refreshToken.expiresAt)
        }

        @Test
        @DisplayName("포맷이 잘못됐을 경우, 예외가 발생한다.")
        fun test2() {
            val tokenValue = "Adfadfadfadf"

            val exception =
                assertThrows<InvalidRefreshTokenFormatException> { refreshTokenParsePort.parse(tokenValue) }
            assertThat(exception.cause).isInstanceOf(JwtException::class.java)
        }

        @Test
        @DisplayName("토큰타입이 '${RefreshToken.VALID_TOKEN_TYPE}'이 아닐 경우 예외가 발생한다.")
        fun test3() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val refreshTokenParsePort = JwtRefreshTokenParseAdapter(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(JwtRefreshTokenParseAdapter.TOKEN_TYPE_CLAIM) } returns "AccessToken"
            every { jwt.claims["iss"] } returns RefreshToken.VALID_ISSUER

            assertThrows<InvalidRefreshTokenFormatException> { refreshTokenParsePort.parse(tokenValue) }
        }

        @Test
        @DisplayName("토큰 발급자가 '${RefreshToken.VALID_ISSUER}'이 아닐 경우 예외가 발생한다.")
        fun test4() {
            val tokenValue = "InvalidTokenTypeToken"
            val jwtDecoder = mockk<JwtDecoder>()
            val jwt = mockk<Jwt>()
            val jwtRefreshTokenParser = JwtRefreshTokenParseAdapter(jwtDecoder)

            every { jwtDecoder.decode(tokenValue) } returns jwt
            every { jwt.getClaim<String>(JwtRefreshTokenParseAdapter.TOKEN_TYPE_CLAIM) } returns RefreshToken.VALID_ISSUER
            every { jwt.claims["iss"] } returns "invalidIssuser"

            assertThrows<InvalidRefreshTokenFormatException> { jwtRefreshTokenParser.parse(tokenValue) }
        }
    }
}
