package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ExternalAccessTokenManager 픽스쳐 테스트")
class ExternalAccessTokenManagerFixtureTest {

    private lateinit var externalAccessTokenManagerFixture: ExternalAccessTokenManagerFixture

    @BeforeEach
    fun setup() {
        externalAccessTokenManagerFixture = ExternalAccessTokenManagerFixture()
    }

    companion object {
        private val log = getLogger(ExternalAccessTokenManagerFixtureTest::class.java)
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
            val expiresAt = timeFixture(minute = 35)

            // when
            val accessToken = externalAccessTokenManagerFixture.generate(authMember, issuedAt, expiresAt)

            log.info { "AccessToken Value: ${accessToken.tokenValue}" }

            // then
            assertThat(accessToken.authMember).isEqualTo(authMember)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
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
            val tokenValue =
                "1,test@gmail.com,testuser,테스트닉네임,USER,1980-01-01T00:05+09:00[Asia/Seoul],1980-01-01T00:35+09:00[Asia/Seoul],accessToken"

            // when
            val accessToken = externalAccessTokenManagerFixture.parse(tokenValue)

            // then
            assertThat(accessToken.authMember).isEqualTo(
                authMemberFixture(
                    memberId = 1L,
                    email = "test@gmail.com",
                    username = "testuser",
                    nickname = "테스트닉네임",
                    role = Role.USER
                )
            )
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(minute = 35))
        }
    }
}
