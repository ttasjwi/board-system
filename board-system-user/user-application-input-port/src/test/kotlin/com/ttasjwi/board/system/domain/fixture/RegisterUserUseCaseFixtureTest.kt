package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.RegisterUserRequest
import com.ttasjwi.board.system.user.domain.RegisterUserUseCase
import com.ttasjwi.board.system.user.domain.fixture.RegisterUserUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterUserUseCaseFixture 테스트")
class RegisterUserUseCaseFixtureTest {

    private lateinit var useCase: RegisterUserUseCase

    @BeforeEach
    fun setUp() {
        useCase = RegisterUserUseCaseFixture()
    }

    @Test
    fun test() {
        val request = RegisterUserRequest(
            email = "hello@gmail.com",
            password = "1111",
            username = "hello",
            nickname = "안뇽",
        )
        val result = useCase.register(request)

        assertThat(result.userId).isNotNull()
        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.username).isEqualTo(request.username)
        assertThat(result.nickname).isEqualTo(request.nickname)
        assertThat(result.role).isNotNull()
        assertThat(result.registeredAt).isNotNull()
    }
}
