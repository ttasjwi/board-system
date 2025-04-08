package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResponse
import com.ttasjwi.board.system.member.application.usecase.fixture.RegisterMemberUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("RegisterMemberController 테스트")
class RegisterMemberControllerTest {

    private lateinit var controller: RegisterMemberController

    @BeforeEach
    fun setup() {
        controller = RegisterMemberController(RegisterMemberUseCaseFixture())
    }

    @Test
    @DisplayName("유즈케이스를 호출하고 201 응답을 반환한다.")
    fun test() {
        // given
        val request = RegisterMemberRequest(
            email = "test@test.com",
            password = "1111",
            username = "testuser",
            nickname = "testnick"
        )

        // when
        val responseEntity = controller.register(request)
        val response = responseEntity.body as RegisterMemberResponse

        // then
        assertThat(responseEntity.statusCode.value()).isEqualTo(HttpStatus.CREATED.value())
        assertThat(response.memberId).isNotNull()
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.username).isEqualTo(request.username)
        assertThat(response.nickname).isEqualTo(request.nickname)
        assertThat(response.role).isNotNull()
        assertThat(response.registeredAt).isNotNull()
    }
}
