package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.SocialService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialConnection 픽스쳐 테스트")
class SocialConnectionFixtureTest {

    @Test
    @DisplayName("디폴트")
    fun test1() {
        // given
        // when
        val socialConnection = socialConnectionFixture()

        // then
        assertThat(socialConnection.socialConnectionId).isNotNull
        assertThat(socialConnection.userId).isNotNull
        assertThat(socialConnection.socialService).isNotNull
        assertThat(socialConnection.socialServiceUserId).isNotNull
        assertThat(socialConnection.linkedAt).isNotNull
    }

    @Test
    @DisplayName("커스텀 파라미터")
    fun test2() {
        // given
        val socialConnectionId = 2L
        val userId = 133L
        val socialService = SocialService.NAVER
        val socialServiceUserId = "adfadf7ad7"
        val linkedAt = appDateTimeFixture(minute = 13)

        // when
        val socialConnection = socialConnectionFixture(
            socialConnectionId = socialConnectionId,
            userId = userId,
            socialService = socialService,
            socialServiceUserId = socialServiceUserId,
            linkedAt = linkedAt
        )

        // then
        assertThat(socialConnection.socialConnectionId).isEqualTo(socialConnectionId)
        assertThat(socialConnection.userId).isEqualTo(userId)
        assertThat(socialConnection.socialService).isEqualTo(socialService)
        assertThat(socialConnection.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(socialConnection.linkedAt).isEqualTo(linkedAt)
    }

}
