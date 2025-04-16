package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.EmailAvailableRequest
import com.ttasjwi.board.system.user.domain.fixture.EmailAvailableUseCaseFixture
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
    @DisplayName("전달받은 이메일이 올바르다는 응답을 내려준다.")
    fun test() {
        // given
        val email = "hello@gmail.com"
        val request = EmailAvailableRequest(email)

        // when
        val response = useCaseFixture.checkEmailAvailable(request)

        // then
        assertThat(response.yourEmail).isEqualTo(email)
        assertThat(response.isAvailable).isTrue()
        assertThat(response.reasonCode).isEqualTo("EmailAvailableCheck.Available")
    }
}
