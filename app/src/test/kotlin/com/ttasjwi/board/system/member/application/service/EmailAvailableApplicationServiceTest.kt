package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.mapper.EmailAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.EmailAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.domain.policy.fixture.EmailFormatPolicyFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailAvailableService: 이메일 사용 가능 여부 확인을 수행하는 애플리케이션 서비스")
class EmailAvailableApplicationServiceTest {

    private lateinit var applicationService: EmailAvailableApplicationService

    @BeforeEach
    fun setup() {
        applicationService = EmailAvailableApplicationService(
            queryMapper = EmailAvailableQueryMapper(),
            processor = EmailAvailableProcessor(
                emailFormatPolicy = EmailFormatPolicyFixture(),
                memberFinder = MemberStorageFixture(),
                messageResolver = MessageResolverFixture(),
                localeManager = LocaleManagerFixture(),
            ),
        )
    }

    @Test
    @DisplayName("이메일 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = EmailAvailableRequest("hello@gmail.com")
        val result = applicationService.checkEmailAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourEmail).isEqualTo(request.email)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("EmailAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("EmailAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("EmailAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
