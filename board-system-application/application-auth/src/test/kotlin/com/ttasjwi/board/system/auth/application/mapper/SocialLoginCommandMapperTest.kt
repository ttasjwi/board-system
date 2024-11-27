package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.service.fixture.EmailCreatorFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("소셜로그인 요청을 애플리케이션 명령으로 변환")
class SocialLoginCommandMapperTest {

    private lateinit var commandMapper: SocialLoginCommandMapper
    private lateinit var timeManagerFixture: TimeManagerFixture

    @BeforeEach
    fun setup() {
        timeManagerFixture = TimeManagerFixture()
        commandMapper = SocialLoginCommandMapper(
            emailCreator = EmailCreatorFixture(),
            timeManager = timeManagerFixture
        )
    }

    @Test
    @DisplayName("변환이 잘 되는 지 테스트")
    fun test() {
        // given
        val currentTime = timeFixture(minute = 13)
        timeManagerFixture.changeCurrentTime(currentTime)

        val request = SocialLoginRequest(
            socialServiceName = "naver",
            socialServiceUserId = "asjsiw761",
            email = "hello@naver.com"
        )

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.socialServiceUser.service.name).isEqualToIgnoringCase(request.socialServiceName)
        assertThat(command.socialServiceUser.userId).isEqualTo(request.socialServiceUserId)
        assertThat(command.email.value).isEqualTo(request.email)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }
}
