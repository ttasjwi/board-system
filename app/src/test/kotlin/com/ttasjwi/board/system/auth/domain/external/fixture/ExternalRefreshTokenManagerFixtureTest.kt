package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ExternalRefreshTokenManager 픽스쳐 테스트")
class ExternalRefreshTokenManagerFixtureTest {

    private lateinit var externalRefreshTokenManagerFixture: ExternalRefreshTokenManagerFixture

    @BeforeEach
    fun setup() {
        externalRefreshTokenManagerFixture = ExternalRefreshTokenManagerFixture()
    }

    companion object {
        private val log = getLogger(ExternalRefreshTokenManagerFixtureTest::class.java)
    }

    @Nested
    @DisplayName("Generate : 액세스토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val memberId = 144L
            val refreshTokenId = refreshTokenIdFixture("abcdef")
            val issuedAt = appDateTimeFixture(minute = 3)
            val expiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 3)

            // when
            val refreshToken =
                externalRefreshTokenManagerFixture.generate(memberId, refreshTokenId, issuedAt, expiresAt)

            log.info { "RefreshToken Value: ${refreshToken.tokenValue}" }

            // then
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
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
                "144,abcdef,2025-01-01T00:03+09:00[Asia/Seoul],2025-01-02T00:03+09:00[Asia/Seoul],refreshToken"
            // when
            val accessToken = externalRefreshTokenManagerFixture.parse(tokenValue)

            // then
            assertThat(accessToken.memberId).isEqualTo(144L)
            assertThat(accessToken.refreshTokenId.value).isEqualTo("abcdef")
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(appDateTimeFixture(minute = 3))
            assertThat(accessToken.expiresAt).isEqualTo(appDateTimeFixture(dayOfMonth = 2, minute = 3))
        }
    }
}
