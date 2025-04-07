package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.application.mapper.UsernameAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.UsernameAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.domain.member.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.domain.member.service.fixture.UsernameManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("UsernameAvailableService: 사용자아이디(username) 사용 가능 여부 확인을 수행하는 애플리케이션 서비스")
class UsernameAvailableApplicationServiceTest {

    private lateinit var applicationService: UsernameAvailableApplicationService

    @BeforeEach
    fun setup() {
        applicationService = UsernameAvailableApplicationService(
            queryMapper = UsernameAvailableQueryMapper(),
            processor = UsernameAvailableProcessor(
                usernameManager = UsernameManagerFixture(),
                memberFinder = MemberStorageFixture(),
                messageResolver = MessageResolverFixture(),
                localeManager = LocaleManagerFixture(),
            ),
        )
    }

    @Test
    @DisplayName("Username 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = UsernameAvailableRequest("hello")
        val result = applicationService.checkUsernameAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourUsername).isEqualTo(request.username)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("UsernameAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("UsernameAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("UsernameAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
