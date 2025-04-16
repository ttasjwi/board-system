package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.user.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.user.domain.EmailVerificationStartResponse
import com.ttasjwi.board.system.user.domain.fixture.EmailVerificationStartUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("EmailVerificationStartController 테스트")
class EmailVerificationStartControllerTest {

    private lateinit var controller: EmailVerificationStartController

    @BeforeEach
    fun setup() {
        controller = EmailVerificationStartController(EmailVerificationStartUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailVerificationStartRequest(email = "hello@gmail.com")

        // when
        val responseEntity = controller.startEmailVerification(request)
        val response = responseEntity.body as EmailVerificationStartResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.codeExpiresAt).isNotNull()
    }
}
