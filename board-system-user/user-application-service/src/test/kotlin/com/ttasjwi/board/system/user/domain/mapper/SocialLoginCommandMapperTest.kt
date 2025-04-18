package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
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
        val container = TestContainer.create()
        timeManagerFixture = container.timeManagerFixture
        commandMapper = container.socialLoginCommandMapper
    }

    @Test
    @DisplayName("변환이 잘 되는 지 테스트")
    fun test() {
        // given
        val currentTime = appDateTimeFixture(minute = 13)
        timeManagerFixture.changeCurrentTime(currentTime)

        val request = SocialLoginRequest(
            socialServiceName = "naver",
            socialServiceUserId = "asjsiw761",
            email = "hello@naver.com"
        )

        // when
        val command = commandMapper.mapToCommand(request)

        // then
        assertThat(command.socialServiceUser.socialService.name).isEqualToIgnoringCase(request.socialServiceName)
        assertThat(command.socialServiceUser.socialServiceUserId).isEqualTo(request.socialServiceUserId)
        assertThat(command.email).isEqualTo(request.email)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }
}
