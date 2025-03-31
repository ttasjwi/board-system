package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginUseCaseFixture 테스트")
class SocialLoginUseCaseFixtureTest {

    private lateinit var usecaseFixture: SocialLoginUseCaseFixture

    @BeforeEach
    fun setup() {
        usecaseFixture = SocialLoginUseCaseFixture()
    }


    @Test
    @DisplayName("소셜로그인 후 소셜로그인 결과를 반환한다.")
    fun test() {
        // given
        val request = SocialLoginRequest(
            "google",
            "asdff567",
            "hello@gmail.com"
        )

        // when
        val result = usecaseFixture.socialLogin(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isTrue()
        assertThat(result.createdMember).isNotNull
    }
}
