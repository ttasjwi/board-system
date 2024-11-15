package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AccessTokenManager 픽스쳐 테스트")
class AccessTokenManagerFixtureTest {

    private lateinit var accessTokenManagerFixture: AccessTokenManagerFixture

    @BeforeEach
    fun setup() {
        accessTokenManagerFixture = AccessTokenManagerFixture()
    }

    companion object {
        private val log = getLogger(AccessTokenManagerFixtureTest::class.java)
    }


    @Nested
    @DisplayName("Generate : 액세스토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val authMember = authMemberFixture()
            val issuedAt = timeFixture(minute = 5)

            // when
            val accessToken = accessTokenManagerFixture.generate(authMember, issuedAt)

            log.info { "AccessToken Value: ${accessToken.tokenValue}" }

            // then
            assertThat(accessToken.authMember).isEqualTo(authMember)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(issuedAt.plusMinutes(AccessTokenManagerFixture.VALIDITY_MINUTES))
            assertThat(accessToken.tokenValue).isNotNull()
        }
    }

    @Nested
    @DisplayName("parse: 문자열로부터 액세스 토큰을 파싱해, 액세스토큰 인스턴스를 생성한다.")
    inner class Parse {

        @Test
        @DisplayName("복원 성공 테스트")
        fun testSuccess() {
            // given
            val authMember = authMemberFixture()
            val issuedAt = timeFixture(minute = 5)
            val tokenValue = accessTokenManagerFixture.generate(authMember, issuedAt).tokenValue
            // when
            val accessToken = accessTokenManagerFixture.parse(tokenValue)

            // then
            assertThat(accessToken.authMember).isEqualTo(authMemberFixture())
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(minute = 35))
        }
    }


    @Nested
    @DisplayName("checkCurrentlyValid: 액세스토큰이 현재 유효한 지 검증한다")
    inner class CheckCurrentlyValid {


        @Test
        @DisplayName("픽스쳐에서는 아무 일도 일어나지 않음")
        fun test() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = timeFixture(minute = 0),
                expiresAt = timeFixture(minute = 2)
            )
            val currentTime = timeFixture(minute = 1)

            // when
            // then
            accessTokenManagerFixture.checkCurrentlyValid(accessToken, currentTime)
        }
    }
}
