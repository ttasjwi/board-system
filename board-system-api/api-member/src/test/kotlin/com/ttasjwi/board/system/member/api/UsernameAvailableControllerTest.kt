package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase
import com.ttasjwi.board.system.member.application.usecase.fixture.UsernameAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("UsernameAvailableController 테스트")
class UsernameAvailableControllerTest {

    private lateinit var controller: UsernameAvailableController
    private lateinit var useCase: UsernameAvailableUseCase
    private lateinit var messageResolver: MessageResolver

    @BeforeEach
    fun setup() {
        useCase = UsernameAvailableUseCaseFixture()
        messageResolver = MessageResolverFixture()
        controller = UsernameAvailableController(useCase, messageResolver)
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 그 결과를 기반으로 200 코드와 함께 응답을 반환한다.")
    fun test() {
        // given
        val request = UsernameAvailableRequest(username = "hello@gmail.com")

        // when
        val responseEntity = controller.checkUsernameAvailable(request)
        val response = responseEntity.body as SuccessResponse<UsernameAvailableResponse>

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("UsernameAvailableCheck.Complete")
        assertThat(response.message).isEqualTo("UsernameAvailableCheck.Complete.message")
        assertThat(response.description).isEqualTo("UsernameAvailableCheck.Complete.description(args=[$.data.usernameAvailable])")

        val usernameAvailable = response.data.usernameAvailable

        assertThat(usernameAvailable.username).isEqualTo(request.username)
        assertThat(usernameAvailable.isAvailable).isEqualTo(true)
        assertThat(usernameAvailable.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
        assertThat(usernameAvailable.message).isEqualTo("UsernameAvailableCheck.Available.message")
        assertThat(usernameAvailable.description).isEqualTo("UsernameAvailableCheck.Available.description(args=[])")
    }
}
