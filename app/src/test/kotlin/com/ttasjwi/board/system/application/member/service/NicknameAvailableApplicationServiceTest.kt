package com.ttasjwi.board.system.application.member.service

import com.ttasjwi.board.system.application.member.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.application.member.processor.NicknameAvailableProcessor
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.global.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.global.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.domain.member.service.MemberStorageFixture
import com.ttasjwi.board.system.domain.member.service.NicknameManagerFixture
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
        applicationService = NicknameAvailableApplicationService(
            queryMapper = NicknameAvailableQueryMapper(),
            processor = NicknameAvailableProcessor(
                nicknameManager = NicknameManagerFixture(),
                memberFinder = MemberStorageFixture(),
                messageResolver = MessageResolverFixture(),
                localeManager = LocaleManagerFixture(),
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
