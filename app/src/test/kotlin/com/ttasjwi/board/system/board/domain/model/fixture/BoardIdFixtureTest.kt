package com.ttasjwi.board.system.board.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 식별자 픽스쳐 테스트")
class BoardIdFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값 value 를 가지고 있다.")
    fun test1() {
        val testId = boardIdFixture()
        assertThat(testId.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 값을 value 로 갖고 있다.")
    fun test2() {
        val value = 334L
        val testId = boardIdFixture(value)
        assertThat(testId.value).isEqualTo(value)
    }
}
