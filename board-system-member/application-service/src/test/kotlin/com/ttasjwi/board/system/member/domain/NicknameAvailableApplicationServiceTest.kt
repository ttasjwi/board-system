package com.ttasjwi.board.system.member.domain

import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.member.domain.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.member.domain.policy.fixturer.NicknamePolicyFixture
import com.ttasjwi.board.system.member.domain.port.fixture.MemberPersistencePortFixture
import com.ttasjwi.board.system.member.domain.processor.NicknameAvailableProcessor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("NicknameAvailableService: 닉네임 사용 가능 여부 확인을 수행하는 애플리케이션 서비스")
class NicknameAvailableApplicationServiceTest {

    private lateinit var applicationService: NicknameAvailableApplicationService

    @BeforeEach
    fun setup() {
        val localeResolverFixture = LocaleResolverFixture()
        localeResolverFixture.changeLocale(Locale.KOREAN)

        applicationService = NicknameAvailableApplicationService(
            queryMapper = NicknameAvailableQueryMapper(
                localeResolver = localeResolverFixture,
            ),
            processor = NicknameAvailableProcessor(
                nicknamePolicy = NicknamePolicyFixture(),
                memberPersistencePort = MemberPersistencePortFixture(),
                messageResolver = MessageResolverFixture(),
            ),
        )
    }

    @Test
    @DisplayName("닉네임 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = NicknameAvailableRequest("hello")
        val result = applicationService.checkNicknameAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.yourNickname).isEqualTo(request.nickname)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
        assertThat(result.reasonMessage).isEqualTo("NicknameAvailableCheck.Available.message(locale=${Locale.KOREAN},args=[])")
        assertThat(result.reasonDescription).isEqualTo("NicknameAvailableCheck.Available.description(locale=${Locale.KOREAN},args=[])")
    }
}
