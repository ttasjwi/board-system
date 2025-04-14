package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("UsernameAvailableUseCaseImpl: 사용자아이디(username) 사용 가능 여부 확인을 수행하는 유즈케이스 구현체")
class UsernameAvailableUseCaseTest {

    private lateinit var useCase: UsernameAvailableUseCase

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        container.localeResolverFixture.changeLocale(Locale.KOREAN)
        useCase = container.usernameAvailableUseCase
    }

    @Test
    @DisplayName("Username 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = UsernameAvailableRequest("hello")
        val result = useCase.checkUsernameAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourUsername).isEqualTo(request.username)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("UsernameAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("UsernameAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
