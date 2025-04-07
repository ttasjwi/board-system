package com.ttasjwi.board.system.domain.auth.external.impl

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.auth.exception.InvalidRefreshTokenFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.oauth2.jwt.JwtException

@DisplayName("ExternalRefreshTokenManagerImpl 테스트")
class ExternalRefreshTokenManagerImplTest : IntegrationTest() {

    private val memberId = 1L
    private val refreshTokenId = "refreshTokenId"
    private val issuedAt = appDateTimeFixture(dayOfMonth = 1)
    private val expiresAt = appDateTimeFixture(dayOfMonth = 2)

    @Nested
    @DisplayName("generate: 리프레시 토큰 인스턴스를 생성한다.")
    inner class GenerateTest {

        @Test
        @DisplayName("생성된 토큰 객체는 전달받은 정보를 가진다.")
        fun test() {
            val refreshToken = externalRefreshTokenManager.generate(memberId, refreshTokenId, issuedAt, expiresAt)

            assertThat(refreshToken.tokenValue).isNotNull()
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 토큰값을 기반으로 리프레시 토큰 객체를 복원한다.")
    inner class Parse {

        @Test
        @DisplayName("복원된 토큰 인스턴스는 생성시점의 토큰 인스턴스와 동등하다.")
        fun test1() {
            val refreshToken = externalRefreshTokenManager.generate(memberId, refreshTokenId, issuedAt, expiresAt)
            val tokenValue = refreshToken.tokenValue
            val parsedRefreshToken = externalRefreshTokenManager.parse(tokenValue)

            assertThat(parsedRefreshToken).isEqualTo(refreshToken)
        }

        @Test
        @DisplayName("포맷이 잘못됐을 경우, 예외가 발생한다.")
        fun test2() {
            // given
            val tokenValue = "adadfadgaghadsaf"

            // when
            val exception =
                assertThrows<InvalidRefreshTokenFormatException> { externalRefreshTokenManager.parse(tokenValue) }

            // then
            assertThat(exception.cause).isInstanceOf(JwtException::class.java)
        }


        @Test
        @DisplayName("액세스 토큰을 파싱하려 시도하면 예외가 발생한다.")
        fun test3() {
            // given
            val authMember = authMemberFixture()
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)
            val tokenValue = accessToken.tokenValue

            // when
            // then
            assertThrows<InvalidRefreshTokenFormatException> { externalRefreshTokenManager.parse(tokenValue) }
        }
    }
}
