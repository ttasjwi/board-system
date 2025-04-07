package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.processor.UsernameAvailableProcessor
import com.ttasjwi.board.system.application.member.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.global.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.global.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.UsernameManagerFixture
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
            queryMapper = com.ttasjwi.board.system.application.member.mapper.UsernameAvailableQueryMapper(),
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
