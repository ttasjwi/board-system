package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.NicknameAvailableRequest
import com.ttasjwi.board.system.user.domain.fixture.NicknameAvailableUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("NicknameAvailableUseCase 픽스쳐 테스트")
class NicknameAvailableUseCaseFixtureTest {

    private lateinit var useCaseFixture: NicknameAvailableUseCaseFixture

    @BeforeEach
    fun setup() {
        useCaseFixture = NicknameAvailableUseCaseFixture()
    }

    @Test
    @DisplayName("전달받은 닉네임이 올바르다는 결과를 내려준다.")
    fun test() {
        // given
        val nickname = "땃쥐"
        val request = NicknameAvailableRequest(nickname)

        // when
        val result = useCaseFixture.checkNicknameAvailable(request)

        // then
        assertThat(result.yourNickname).isEqualTo(nickname)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
    }
}
