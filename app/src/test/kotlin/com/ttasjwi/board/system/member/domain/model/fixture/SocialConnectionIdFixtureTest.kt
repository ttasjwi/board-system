package com.ttasjwi.board.system.member.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionId Fixture 테스트")
class SocialConnectionIdFixtureTest {

    @Test
    @DisplayName("디폴트 파라미터")
    fun test1() {
        // given
        // when
        val socialConnectionId = socialConnectionIdFixture()

        // then
        assertThat(socialConnectionId).isNotNull
    }


    @Test
    @DisplayName("커스텀 파라미터")
    fun test2() {
        // given
        val value = 3L

        // when
        val socialConnectionId = socialConnectionIdFixture(value)

        // then
        assertThat(socialConnectionId.value).isEqualTo(value)
    }
}
