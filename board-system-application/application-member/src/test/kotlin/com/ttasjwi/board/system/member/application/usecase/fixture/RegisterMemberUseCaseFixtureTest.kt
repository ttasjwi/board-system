package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterMemberUseCaseFixture 테스트")
class RegisterMemberUseCaseFixtureTest {

    private lateinit var useCase: RegisterMemberUseCase

    @BeforeEach
    fun setUp() {
        useCase = RegisterMemberUseCaseFixture()
    }

    @Test
    fun test() {
        val request = RegisterMemberRequest(
            email = "hello@gmail.com",
            password = "1111",
            username = "hello",
            nickname = "안뇽",
        )
        val result = useCase.register(request)

        assertThat(result.memberId).isNotNull()
        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.username).isEqualTo(request.username)
        assertThat(result.nickname).isEqualTo(request.nickname)
        assertThat(result.role).isNotNull()
        assertThat(result.registeredAt).isNotNull()
    }
}
