package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.fixture.ExternalRefreshTokenManagerFixture
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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
            val memberId = memberIdFixture(144L)
            val issuedAt = timeFixture(minute = 3)

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
                "144,abcdef,1980-01-01T00:03+09:00[Asia/Seoul],1980-01-02T00:03+09:00[Asia/Seoul],refreshToken"
            // when
            val accessToken = refreshTokenManager.parse(tokenValue)

            // then
            assertThat(accessToken.memberId.value).isEqualTo(144L)
            assertThat(accessToken.refreshTokenId.value).isEqualTo("abcdef")
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 3))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(dayOfMonth = 2, minute = 3))
        }
    }
}
