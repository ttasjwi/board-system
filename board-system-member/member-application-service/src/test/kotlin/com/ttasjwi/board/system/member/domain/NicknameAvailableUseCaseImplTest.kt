package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("NicknameAvailableUseCaseImpl: 닉네임 사용 가능 여부 확인을 수행하는 유크케이스 구현체")
class NicknameAvailableUseCaseImplTest {

    private lateinit var useCase: NicknameAvailableUseCase

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        container.localeResolverFixture.changeLocale(Locale.KOREAN)
        useCase = container.nicknameAvailableUseCase
    }

    @Test
    @DisplayName("닉네임 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = NicknameAvailableRequest("hello")
        val result = useCase.checkNicknameAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourNickname).isEqualTo(request.nickname)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
