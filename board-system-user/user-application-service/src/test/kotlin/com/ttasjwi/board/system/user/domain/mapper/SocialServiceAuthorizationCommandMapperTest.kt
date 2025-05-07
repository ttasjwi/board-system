package com.ttasjwi.board.system.user.domain.mapper

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SocialServiceAuthorizationCommandMapperTest {

    private lateinit var commandMapper: SocialServiceAuthorizationCommandMapper
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        commandMapper = container.socialServiceAuthorizationCommandMapper

        currentTime = appDateTimeFixture(minute = 13)
        container.timeManagerFixture.changeCurrentTime(currentTime)
    }


    @Test
    @DisplayName("명령 생성 테스트")
    fun test() {
        // given
        val socialServiceId = "google"
        val command = commandMapper.mapToCommand(socialServiceId)

        assertThat(command.oAuth2ClientRegistrationId).isEqualTo(socialServiceId)
        assertThat(command.currentTime).isEqualTo(currentTime)
    }
}
