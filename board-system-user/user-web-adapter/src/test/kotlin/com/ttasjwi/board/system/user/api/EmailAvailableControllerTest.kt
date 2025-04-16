package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.user.domain.EmailAvailableRequest
import com.ttasjwi.board.system.user.domain.EmailAvailableResponse
import com.ttasjwi.board.system.user.domain.fixture.EmailAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("EmailAvailableController 테스트")
class EmailAvailableControllerTest {

    private lateinit var controller: EmailAvailableController

    @BeforeEach
    fun setup() {
        controller = EmailAvailableController(EmailAvailableUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = EmailAvailableRequest(email = "hello@gmail.com")

        // when
        val responseEntity = controller.checkEmailAvailable(request)
        val response = responseEntity.body as EmailAvailableResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.yourEmail).isEqualTo(request.email)
        assertThat(response.isAvailable).isEqualTo(true)
        assertThat(response.reasonCode).isEqualTo("EmailAvailableCheck.Available")
        assertThat(response.reasonMessage).isEqualTo("사용 가능한 이메일")
        assertThat(response.reasonDescription).isEqualTo("이 이메일은 사용 가능합니다.")
    }
}
