package com.ttasjwi.board.system.board.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 이름 픽스쳐 테스트")
class BoardNameFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값 value 를 가지고 있다.")
    fun test1() {
        val testName = boardNameFixture()
        assertThat(testName.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 값을 value 로 갖고 있다.")
    fun test2() {
        val value = "이름222"
        val testName = boardNameFixture(value)

        assertThat(testName.value).isEqualTo(value)
    }
}
