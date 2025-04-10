package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.domain.mapper.UsernameAvailableQueryMapper
import com.ttasjwi.board.system.member.domain.policy.fixturer.UsernamePolicyFixture
import com.ttasjwi.board.system.member.domain.port.fixture.MemberPersistencePortFixture
import com.ttasjwi.board.system.member.domain.processor.UsernameAvailableProcessor
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
        val localeResolverFixture = LocaleResolverFixture()
        localeResolverFixture.changeLocale(Locale.KOREAN)
        applicationService = UsernameAvailableApplicationService(
            queryMapper = UsernameAvailableQueryMapper(
                localeResolver = localeResolverFixture
            ),
            processor = UsernameAvailableProcessor(
                usernamePolicy = UsernamePolicyFixture(),
                memberPersistencePort = MemberPersistencePortFixture(),
                messageResolver = MessageResolverFixture(),
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
