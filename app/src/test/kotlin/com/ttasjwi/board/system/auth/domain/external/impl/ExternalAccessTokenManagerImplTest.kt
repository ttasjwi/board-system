package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.auth.domain.exception.InvalidAccessTokenFormatException
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.oauth2.jwt.JwtException

@DisplayName("ExternalAccessTokenManagerImpl 테스트")
class ExternalAccessTokenManagerImplTest : IntegrationTest() {

    private val authMember = authMemberFixture(
        memberId = 1L,
        role = Role.USER
    )
    private val issuedAt = timeFixture(minute = 0)
    private val expiresAt = timeFixture(minute = 30)

    @Nested
    @DisplayName("generate: 로그인 한 회원의 인증정보를 담은 액세스 토큰 인스턴스를 생성한다.")
    inner class GenerateTest {

        @Test
        @DisplayName("생성된 토큰 인스턴스는 토큰값을 가진다.")
        fun test() {
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)

            assertThat(accessToken.tokenValue).isNotNull()
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 인증된 회원의 정보를 가진다.")
        fun test2() {
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)

            assertThat(accessToken.authMember.memberId).isEqualTo(authMember.memberId)
            assertThat(accessToken.authMember.role).isEqualTo(authMember.role)
        }

        @Test
        @DisplayName("생성된 토큰 객체는 전달받은 발급시간 정보를 가진다")
        fun test3() {
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)

            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
        }

        @Test
        @DisplayName("생성된 토큰 인스턴스는 전달받은 만료시간 정보를 가진다.")
        fun test4() {
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 토큰값을 기반으로 액세스 토큰 객체를 복원한다.")
    inner class Parse {

        @Test
        @DisplayName("복원된 토큰 객체는 처음 생성시점의 액세스 토큰과 동등하다.")
        fun test1() {
            val accessToken = externalAccessTokenManager.generate(authMember, issuedAt, expiresAt)
            val tokenValue = accessToken.tokenValue
            val parsedAccessToken = externalAccessTokenManager.parse(tokenValue)

            assertThat(parsedAccessToken).isEqualTo(accessToken)
        }

        @Test
        @DisplayName("포맷이 잘못됐을 경우, 예외가 발생한다.")
        fun test2() {
            val tokenValue = "Adfadfadfadf"

            val exception =
                assertThrows<InvalidAccessTokenFormatException> { externalAccessTokenManager.parse(tokenValue) }
            assertThat(exception.cause).isInstanceOf(JwtException::class.java)
        }

        @Test
        @DisplayName("리프레시 토큰을 파싱하려 시도하면 예외가 발생한다.")
        fun test3() {
            val memberId = 1L
            val refreshTokenId = refreshTokenIdFixture("abcdef")
            val refreshToken = externalRefreshTokenManager.generate(memberId, refreshTokenId, issuedAt, expiresAt)
            val tokenValue = refreshToken.tokenValue

            assertThrows<InvalidAccessTokenFormatException> { externalAccessTokenManager.parse(tokenValue) }
        }
    }
}
