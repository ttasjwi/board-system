package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("NicknameAvailableController 테스트")
class NicknameAvailableControllerTest {

    private lateinit var controller: NicknameAvailableController

    @BeforeEach
    fun setup() {
        controller = NicknameAvailableController(NicknameAvailableUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 200 응답을 반환한다.")
    fun test() {
        // given
        val request = NicknameAvailableRequest(nickname = "hello")

        // when
        val responseEntity = controller.checkNicknameAvailable(request)
        val response = responseEntity.body as NicknameAvailableResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.OK.value())
        assertThat(response.yourNickname).isEqualTo(request.nickname)
        assertThat(response.isAvailable).isEqualTo(true)
        assertThat(response.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
        assertThat(response.reasonMessage).isEqualTo("사용 가능한 닉네임")
        assertThat(response.reasonDescription).isEqualTo("이 닉네임은 사용 가능합니다.")
    }
}
