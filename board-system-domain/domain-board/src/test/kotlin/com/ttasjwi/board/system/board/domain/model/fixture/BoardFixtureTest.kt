package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 픽스쳐 테스트")
class BoardFixtureTest {

    @Nested
    @DisplayName("boardFixtureNotRegistered: 저장되지 않은 게시판을 테스트용으로 생성")
    inner class NotRegistered {

        @Test
        @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이다.")
        fun test1() {
            val board = boardFixtureNotRegistered()

            assertThat(board.id).isNull()
            assertThat(board.name).isNotNull
            assertThat(board.description).isNotNull
            assertThat(board.managerId).isNotNull
            assertThat(board.slug).isNotNull
            assertThat(board.createdAt).isNotNull
        }

        @Test
        @DisplayName("커스텀하게 인자를 지정할 수 있으며, 이 때 id는 null 이다.")
        fun test2() {
            val name = "경제"
            val description = "경제 게시판입니다."
            val managerId = 13L
            val slug = "economy"
            val createdAt = timeFixture()
            val board = boardFixtureNotRegistered(
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = createdAt
            )

            assertThat(board.id).isNull()
            assertThat(board.name).isEqualTo(boardNameFixture(name))
            assertThat(board.description).isEqualTo(boardDescriptionFixture(description))
            assertThat(board.managerId).isEqualTo(memberIdFixture(managerId))
            assertThat(board.slug).isEqualTo(boardSlugFixture(slug))
            assertThat(board.createdAt).isEqualTo(createdAt)
        }
    }

    @Nested
    @DisplayName("boardFixtureRegistered: 저장된 게시판을 테스트용으로 생성")
    inner class Registered {

        @Test
        @DisplayName("인자 없이 생성해도 기본값을 가지며, id는 null 이 아니다.")
        fun test1() {
            val board = boardFixtureRegistered()

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
            val createdAt = timeFixture()
            val board = boardFixtureRegistered(
                id = id,
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = createdAt
            )

            assertThat(board.id).isEqualTo(boardIdFixture(id))
            assertThat(board.name).isEqualTo(boardNameFixture(name))
            assertThat(board.description).isEqualTo(boardDescriptionFixture(description))
            assertThat(board.managerId).isEqualTo(memberIdFixture(managerId))
            assertThat(board.slug).isEqualTo(boardSlugFixture(slug))
            assertThat(board.createdAt).isEqualTo(createdAt)
        }
    }
}
