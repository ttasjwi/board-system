package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.user.domain.RegisterUserRequest
import com.ttasjwi.board.system.user.domain.RegisterUserResponse
import com.ttasjwi.board.system.user.domain.fixture.RegisterUserUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("RegisterUserController 테스트")
class RegisterUserControllerTest {

    private lateinit var controller: RegisterUserController

    @BeforeEach
    fun setup() {
        controller = RegisterUserController(RegisterUserUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 201 응답을 반환한다.")
    fun test() {
        // given
        val request = RegisterUserRequest(
            email = "test@test.com",
            password = "1111",
            username = "testuser",
            nickname = "testnick"
        )

        // when
        val responseEntity = controller.register(request)
        val response = responseEntity.body as RegisterUserResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.userId).isNotNull()
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.username).isEqualTo(request.username)
        assertThat(response.nickname).isEqualTo(request.nickname)
        assertThat(response.role).isNotNull()
        assertThat(response.registeredAt).isNotNull()
    }
}
