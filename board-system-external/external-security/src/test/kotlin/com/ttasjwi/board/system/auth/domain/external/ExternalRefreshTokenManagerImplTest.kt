package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.exception.InvalidRefreshTokenFormatException
import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.jwt.JwtException

@SpringBootTest
@DisplayName("ExternalRefreshTokenManagerImpl 테스트")
class ExternalRefreshTokenManagerImplTest
@Autowired constructor(
    private val externalRefreshTokenManager: ExternalRefreshTokenManager,
    private val externalAccessTokenManager: ExternalAccessTokenManager,
) {

    private val memberId = memberIdFixture(1L)
    private val refreshTokenId = refreshTokenIdFixture("refreshTokenId")
    private val issuedAt = timeFixture(dayOfMonth = 1)
    private val expiresAt = timeFixture(dayOfMonth = 2)

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
            val exception = assertThrows<InvalidRefreshTokenFormatException> { externalRefreshTokenManager.parse(tokenValue) }

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
