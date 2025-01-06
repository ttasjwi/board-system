package com.ttasjwi.board.system.board.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 설명 픽스쳐 테스트")
class BoardDescriptionFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값 value 를 가지고 있다.")
    fun test1() {
        val testDescription = boardDescriptionFixture()
        assertThat(testDescription.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 값을 value 로 갖고 있다.")
    fun test2() {
        val value = "게시판 설명111"
        val testDescription = boardDescriptionFixture(value)

        assertThat(testDescription.value).isEqualTo(value)
    }
}
