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
        assertThat(socialConnection.memberId).isNotNull
        assertThat(socialConnection.socialServiceUser).isNotNull
        assertThat(socialConnection.linkedAt).isNotNull
    }

    @Test
    @DisplayName("커스텀 파라미터")
    fun test2() {
        // given
        val id = 2L
        val memberId = 133L
        val socialService = SocialService.NAVER
        val socialServiceUserId = "adfadf7ad7"
        val linkedAt = appDateTimeFixture(minute = 13)

        // when
        val socialConnection = socialConnectionFixture(
            id = id,
            memberId = memberId,
            socialService = socialService,
            socialServiceUserId = socialServiceUserId,
            linkedAt = linkedAt
        )

        // then
        assertThat(socialConnection.socialConnectionId).isEqualTo(id)
        assertThat(socialConnection.memberId).isEqualTo(memberId)
        assertThat(socialConnection.socialServiceUser.socialService).isEqualTo(socialService)
        assertThat(socialConnection.socialServiceUser.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(socialConnection.linkedAt).isEqualTo(linkedAt)
    }

}
