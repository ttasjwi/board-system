package com.ttasjwi.board.system.board.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 슬러그 픽스쳐 테스트")
class BoardSlugFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값 value 를 가지고 있다.")
    fun test1() {
        val testSlug = boardSlugFixture()
        assertThat(testSlug.value).isNotNull()
    }

    @Test
    @DisplayName("호출시 전달한 값을 value 로 갖고 있다.")
    fun test2() {
        val value = "sluggg"
        val testSlug = boardSlugFixture(value)

        assertThat(testSlug.value).isEqualTo(value)
    }
}
