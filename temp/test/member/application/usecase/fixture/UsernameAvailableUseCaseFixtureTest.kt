package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UsernameAvailableUseCase 픽스쳐 테스트")
class UsernameAvailableUseCaseFixtureTest {

    private lateinit var useCaseFixture: UsernameAvailableUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = UsernameAvailableUseCaseFixture()
    }

    @Test
    @DisplayName("전달받은 사용자아이디가 올바르다는 결과를 내려준다.")
    fun test() {
        // given
        val username = "hello"
        val request = UsernameAvailableRequest(username)

        // when
        val result = useCaseFixture.checkUsernameAvailable(request)

        // then
        assertThat(result.yourUsername).isEqualTo(username)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
    }
}
