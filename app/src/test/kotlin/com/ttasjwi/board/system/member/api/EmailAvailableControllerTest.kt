package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.api.SuccessResponse
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.fixture.EmailAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.*

@DisplayName("EmailAvailableController 테스트")
class EmailAvailableControllerTest {

    private lateinit var controller: EmailAvailableController
    private lateinit var useCaseFixture: EmailAvailableUseCaseFixture
    private lateinit var messageResolverFixture: MessageResolverFixture
    private lateinit var localeManagerFixture: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = EmailAvailableUseCaseFixture()
        messageResolverFixture = MessageResolverFixture()
        localeManagerFixture = LocaleManagerFixture()
        controller = EmailAvailableController(useCaseFixture, messageResolverFixture, localeManagerFixture)
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailAvailableRequest(email = "hello@gmail.com")

        // when
        val responseEntity = controller.checkEmailAvailable(request)
        val response = responseEntity.body as SuccessResponse<EmailAvailableResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("EmailAvailableCheck.Complete")
        assertThat(response.message).isEqualTo("EmailAvailableCheck.Complete.message(locale=${Locale.KOREAN},args=[])")
        assertThat(response.description).isEqualTo("EmailAvailableCheck.Complete.description(locale=${Locale.KOREAN},args=[\$.data.emailAvailable])")

        val emailAvailable = response.data.emailAvailable

        assertThat(emailAvailable.email).isEqualTo(request.email)
        assertThat(emailAvailable.isAvailable).isEqualTo(true)
        assertThat(emailAvailable.reasonCode).isEqualTo("EmailAvailableCheck.Available")
        assertThat(emailAvailable.message).isEqualTo("EmailAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(emailAvailable.description).isEqualTo("EmailAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
