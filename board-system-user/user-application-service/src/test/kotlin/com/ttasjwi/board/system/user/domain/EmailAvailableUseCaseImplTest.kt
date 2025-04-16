package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailAvailableUseCaseImpl: 이메일 사용 가능 여부 확인을 수행하는 애플리케이션 서비스")
class EmailAvailableUseCaseImplTest {

    private lateinit var useCase: EmailAvailableUseCase

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        container.localeResolverFixture.changeLocale(Locale.KOREAN)

        useCase = container.emailAvailableUseCase
    }

    @Test
    @DisplayName("이메일 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = EmailAvailableRequest("hello@gmail.com")
        val result = useCase.checkEmailAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourEmail).isEqualTo(request.email)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("EmailAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("EmailAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
