package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenManager 픽스쳐 테스트")
class RefreshTokenManagerFixtureTest {

    private lateinit var refreshTokenManagerFixture: RefreshTokenManagerFixture

    @BeforeEach
    fun setup() {
        refreshTokenManagerFixture = RefreshTokenManagerFixture()
    }

    companion object {
        private val log = getLogger(RefreshTokenManagerFixtureTest::class.java)
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
            val refreshToken = refreshTokenManagerFixture.generate(memberId, issuedAt)

            log.info { "RefreshToken Value: ${refreshToken.tokenValue}" }

            // then
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isNotNull
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(issuedAt.plusHours(RefreshTokenManagerFixture.VALIDITY_HOURS))
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
                "144,abcd,1980-01-01T00:03+09:00[Asia/Seoul],1980-01-02T00:03+09:00[Asia/Seoul],refreshToken"
            // when
            val accessToken = refreshTokenManagerFixture.parse(tokenValue)

            // then
            assertThat(accessToken.memberId.value).isEqualTo(144L)
            assertThat(accessToken.refreshTokenId.value).isEqualTo("abcd")
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 3))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(dayOfMonth = 2, minute = 3))
        }
    }
}
