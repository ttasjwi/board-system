package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.application.usecase.fixture.EmailVerificationStartUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("EmailVerificationStartController 테스트")
class EmailVerificationStartControllerTest {

    private lateinit var controller: EmailVerificationStartController
    private lateinit var useCaseFixture: EmailVerificationStartUseCaseFixture
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = EmailVerificationStartUseCaseFixture()
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        controller = EmailVerificationStartController(
            useCase = useCaseFixture,
            messageResolver = messageResolverFixture,
            localeManager = localeManagerFixture
        )
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailVerificationStartRequest(email = "hello@gmail.com")

        // when
        val responseEntity = controller.startEmailVerification(request)
        val response = responseEntity.body as SuccessResponse<EmailVerificationStartResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("EmailVerificationStart.Complete")
        assertThat(response.message).isEqualTo("EmailVerificationStart.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("EmailVerificationStart.Complete.description(locale=${Locale.KOREAN},args=[])")

        val emailVerificationStartResult = response.data.emailVerificationStartResult

        assertThat(emailVerificationStartResult.email).isEqualTo(request.email)
        assertThat(emailVerificationStartResult.codeExpiresAt).isEqualTo(timeFixture())
    }
}
