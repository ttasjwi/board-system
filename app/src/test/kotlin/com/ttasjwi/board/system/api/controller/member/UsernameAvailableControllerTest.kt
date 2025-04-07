package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResponse
import com.ttasjwi.board.system.member.application.usecase.fixture.UsernameAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("UsernameAvailableController 테스트")
class UsernameAvailableControllerTest {

    private lateinit var controller: UsernameAvailableController

    @BeforeEach
    fun setup() {
        controller = UsernameAvailableController(UsernameAvailableUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = UsernameAvailableRequest(username = "hello@gmail.com")

        // when
        val responseEntity = controller.checkUsernameAvailable(request)
        val response = responseEntity.body as UsernameAvailableResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.yourUsername).isEqualTo(request.username)
        assertThat(response.isAvailable).isEqualTo(true)
        assertThat(response.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
        assertThat(response.reasonMessage).isEqualTo("사용 가능한 사용자아이디(username)")
        assertThat(response.reasonDescription).isEqualTo("이 사용자아이디(username)는 사용 가능합니다.")
    }
}
