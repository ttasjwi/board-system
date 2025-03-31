package com.ttasjwi.board.system.auth.domain.model.fixture

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenId 픽스쳐 테스트")
class RefreshTokenIdFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val refreshTokenId = refreshTokenIdFixture()

        // then
        assertThat(refreshTokenId.value).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val value = "refreshTokenId222"
        // when
        val refreshTokenId = refreshTokenIdFixture(value)

        // then
        assertThat(refreshTokenId.value).isEqualTo(value)
    }
}
