package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.domain.model.Role
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

            log.info{ "AccessToken Value: ${accessToken.tokenValue}"}

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
            val tokenValue = "1,test@gmail.com,testuser,테스트닉네임,USER,1980-01-01T00:05+09:00[Asia/Seoul],1980-01-01T00:35+09:00[Asia/Seoul],accessToken"

            // when
            val accessToken = accessTokenManagerFixture.parse(tokenValue)

            // then
            assertThat(accessToken.authMember).isEqualTo(authMemberFixture(
                memberId = 1L,
                email = "test@gmail.com",
                username = "testuser",
                nickname = "테스트닉네임",
                role = Role.USER
            ))
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(timeFixture(minute = 5))
            assertThat(accessToken.expiresAt).isEqualTo(timeFixture(minute = 35))
        }
    }
}
