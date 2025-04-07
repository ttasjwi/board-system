package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.domain.auth.model.boardFixture
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시판 픽스쳐 테스트")
class BoardFixtureTest {

    @Test
    @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이 아니다.")
    fun test1() {
        val board = boardFixture()

        assertThat(board.id).isNotNull
        assertThat(board.name).isNotNull
        assertThat(board.description).isNotNull
        assertThat(board.managerId).isNotNull
        assertThat(board.slug).isNotNull
        assertThat(board.createdAt).isNotNull
    }

    @Test
    @DisplayName("커스텀하게 인자를 지정할 수 있다.")
    fun test2() {
        val id = 123434L
        val name = "경제"
        val description = "경제 게시판입니다."
        val managerId = 13L
        val slug = "economy"
        val createdAt = appDateTimeFixture()
        val board = boardFixture(
            id = id,
            name = name,
            description = description,
            managerId = managerId,
            slug = slug,
            createdAt = createdAt
        )

        assertThat(board.id).isEqualTo(id)
        assertThat(board.name).isEqualTo(name)
        assertThat(board.description).isEqualTo(description)
        assertThat(board.managerId).isEqualTo(managerId)
        assertThat(board.slug).isEqualTo(slug)
        assertThat(board.createdAt).isEqualTo(createdAt)
    }
}
