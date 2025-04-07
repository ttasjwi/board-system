package com.ttasjwi.board.system.domain.member.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialServiceUser 픽스쳐")
class SocialServiceUserFixtureTest {

    @Test
    @DisplayName("디폴트")
    fun test1() {
        // given
        // when
        val socialServiceUser = socialServiceUserFixture()

        // then
        assertThat(socialServiceUser.service).isNotNull()
        assertThat(socialServiceUser.userId).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 지정")
    fun test2() {
        // given
        val socialService = SocialService.NAVER
        val socialServiceUserId = "1224445a"

        // when
        val socialServiceUser = socialServiceUserFixture(
            socialService = socialService,
            socialServiceUserId = socialServiceUserId
        )

        // then
        assertThat(socialServiceUser.service).isEqualTo(socialService)
        assertThat(socialServiceUser.userId).isEqualTo(socialServiceUserId)
    }
}
