package com.ttasjwi.board.user.adapter.input.web

import com.ttasjwi.board.user.adapter.input.web.request.UserRegisterRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("UserController 테스트")
class UserControllerTest {

    @Test
    @DisplayName("사용자는 회원가입할 수 있다.")
    fun registerSuccessTest() {
        // given
        val controller = UserController()
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
}
