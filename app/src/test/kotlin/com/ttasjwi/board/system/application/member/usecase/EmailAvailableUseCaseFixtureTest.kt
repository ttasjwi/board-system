package com.ttasjwi.board.system.application.member.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailAvailableUseCase 픽스쳐 테스트")
class EmailAvailableUseCaseFixtureTest {

    private lateinit var useCaseFixture: EmailAvailableUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = EmailAvailableUseCaseFixture()
    }

    @Test
    @DisplayName("전달받은 이메일이 올바르다는 결과를 내려준다.")
    fun test() {
        // given
        val email = "hello@gmail.com"
        val request = EmailAvailableRequest(email)

        // when
        val result = useCaseFixture.checkEmailAvailable(request)

        // then
        assertThat(result.yourEmail).isEqualTo(email)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Available")
    }
}
