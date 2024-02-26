package com.ttasjwi.board.user.adapter.input.web

import com.ttasjwi.board.user.adapter.input.web.request.UserRegisterRequest
import com.ttasjwi.board.user.application.usecase.UserRegisterCommand
import com.ttasjwi.board.user.application.usecase.UserRegisterResult
import com.ttasjwi.board.user.application.usecase.UserRegisterUseCase
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("UserController 테스트")
class UserControllerTest {

    @Test
    @DisplayName("사용자가 회원가입에 성공하면 201 응답을 받는다.")
    fun registerSuccessTest() {
        // given
        val controller = UserController(userRegisterUseCase = fakeUserRegisterUseCase())

        val request = UserRegisterRequest("ttasjwi", "땃쥐", "ttasjwi@gmail.com", "1234")

        // when
        val result = controller.registerUser(request)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body).isNotNull()
        assertThat(result.body!!.id).isNotNull()
        assertThat(result.body!!.loginId).isEqualTo(request.loginId)
        assertThat(result.body!!.email).isEqualTo(request.email)
        assertThat(result.body!!.nickname).isEqualTo(request.nickname)
        assertThat(result.body!!.role).isEqualTo("USER")
    }

    private fun fakeUserRegisterUseCase() = object : UserRegisterUseCase {

        override fun register(command: UserRegisterCommand): UserRegisterResult {
            return UserRegisterResult(
                id = 1L,
                loginId = command.loginId,
                nickname = command.nickname,
                email = command.email,
                role = "USER"
            )
        }
    }
}
