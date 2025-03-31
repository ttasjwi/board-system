package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.application.usecase.fixture.EmailVerificationUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("EmailVerificationController 테스트")
class EmailVerificationControllerTest {


    private lateinit var controller: EmailVerificationController
    private lateinit var useCaseFixture: EmailVerificationUseCaseFixture
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = EmailVerificationUseCaseFixture()
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        controller = EmailVerificationController(
            useCase = useCaseFixture,
            messageResolver = messageResolverFixture,
            localeManager = localeManagerFixture
        )
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailVerificationRequest(email = "hello@gmail.com", code = "1234")

        // when
        val responseEntity = controller.emailVerification(request)
        val response = responseEntity.body as SuccessResponse<EmailVerificationResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("EmailVerification.Complete")
        assertThat(response.message).isEqualTo("EmailVerification.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("EmailVerification.Complete.description(locale=${Locale.KOREAN},args=[])")

        val emailVerificationStartResult = response.data.verificationResult

        assertThat(emailVerificationStartResult.email).isEqualTo(request.email)
        assertThat(emailVerificationStartResult.verificationExpiresAt).isNotNull()
    }
}
