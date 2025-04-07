package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationResponse
import com.ttasjwi.board.system.application.member.usecase.EmailVerificationUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("EmailVerificationController 테스트")
class EmailVerificationControllerTest {

    private lateinit var controller: EmailVerificationController

    @BeforeEach
    fun setup() {
        controller = EmailVerificationController(EmailVerificationUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailVerificationRequest(email = "hello@gmail.com", code = "1234")

        // when
        val responseEntity = controller.emailVerification(request)
        val response = responseEntity.body as EmailVerificationResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.verificationExpiresAt).isNotNull()
    }
}
