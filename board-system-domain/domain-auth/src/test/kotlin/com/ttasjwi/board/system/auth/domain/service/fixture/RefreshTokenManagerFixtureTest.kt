package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
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

    @Nested
    @DisplayName("checkCurrentlyValid: 리프레시토큰이 현재 유효한 지 검증")
    inner class CheckCurrentlyValid {


        @Test
        @DisplayName("아무 것도 하지 않는다")
        fun test() {
            // given
            val refreshToken = refreshTokenFixture()
            val refreshTokenHolder = refreshTokenHolderFixture()
            val currentTime = timeFixture()

            // when
            // then
            refreshTokenManagerFixture.checkCurrentlyValid(refreshToken, refreshTokenHolder, currentTime)
        }
    }


    @Nested
    @DisplayName("isRefreshRequired: 리프레시토큰이 재갱신이 필요한 지 여부를 확인한다")
    inner class IsRefreshRequired {

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 ${RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전보다 이전 시간이면 재생신 필요가 없다.")
        fun testRefreshNotRequired() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = timeFixture(dayOfMonth = 1),
                expiresAt = timeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS)
                .minusNanos(1)

            // when
            val isRefreshRequired = refreshTokenManagerFixture.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isFalse()
        }

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 정확히 ${RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전이면 재생신해야한다.")
        fun testRequired1() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = timeFixture(dayOfMonth = 1),
                expiresAt = timeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS)

            // when
            val isRefreshRequired = refreshTokenManagerFixture.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isTrue()
        }

        @Test
        @DisplayName("리프레시토큰의 만료시간 기준 ${RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS} 시간 전보다 이후면 재생신해야한다.")
        fun testRequired2() {
            // given
            val refreshToken = refreshTokenFixture(
                memberId = 123L,
                refreshTokenId = "abc",
                issuedAt = timeFixture(dayOfMonth = 1),
                expiresAt = timeFixture(dayOfMonth = 2),
            )
            val currentTime = refreshToken.expiresAt
                .minusHours(RefreshTokenManagerFixture.REFRESH_REQUIRE_THRESHOLD_HOURS)
                .plusNanos(1)

            // when
            val isRefreshRequired = refreshTokenManagerFixture.isRefreshRequired(refreshToken, currentTime)

            // then
            assertThat(isRefreshRequired).isTrue()
        }
    }
}
