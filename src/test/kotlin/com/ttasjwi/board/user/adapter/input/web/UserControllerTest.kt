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

    private val controller = UserController(userRegisterUseCase = object : UserRegisterUseCase {

        override fun register(command: UserRegisterCommand): UserRegisterResult {
            return UserRegisterResult(
                id = 1L,
                loginId = command.loginId,
                nickname = command.nickname,
                email = command.email,
                role = "USER"
            )
        }
    })

    @Test
    @DisplayName("사용자가 회원가입에 성공하면 201 응답을 받는다.")
    fun registerSuccessTest() {
        // given
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

    @Test
    @DisplayName("회원가입 시 로그인Id는 필수이다.")
    fun requireLoginId() {
        // given
        // when
        val request = UserRegisterRequest(null, "땃쥐", "ttasjwi@gmail.com", "1234")

        assertThatThrownBy { controller.registerUser(request) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("회원가입 시 닉네임은 필수이다.")
    fun requireNickName() {
        // given
        // when
        val request = UserRegisterRequest("ttasjwi", null, "ttasjwi@gmail.com", "1234")

        assertThatThrownBy { controller.registerUser(request) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("회원가입 시 이메일은 필수이다.")
    fun requireEmail() {
        // given
        // when
        val request = UserRegisterRequest(null, "땃쥐", "ttasjwi@gmail.com", "1234")

        assertThatThrownBy { controller.registerUser(request) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("회원가입 시 패스워드는 필수이다.")
    fun requirePassword() {
        // given
        // when
        val request = UserRegisterRequest("ttasjwi", "땃쥐", "ttasjwi@gmail.com", null)

        assertThatThrownBy { controller.registerUser(request) }.isInstanceOf(IllegalArgumentException::class.java)
    }

}
