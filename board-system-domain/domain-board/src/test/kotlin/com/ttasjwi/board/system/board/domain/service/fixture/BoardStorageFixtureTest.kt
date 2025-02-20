package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("BoardManager 픽스쳐 테스트")
class BoardStorageFixtureTest {

    private lateinit var boardStorageFixture: BoardStorageFixture

    @BeforeEach
    fun setup() {
        boardStorageFixture = BoardStorageFixture()
    }

    @DisplayName("save 테스트")
    @Nested
    inner class Save {

        @Test
        @DisplayName("save 후 아이디가 생성된다")
        fun test() {
            // given
            val board = boardFixtureNotRegistered()

            // when
            val savedBoard = boardStorageFixture.save(board)

            // then
            assertThat(savedBoard.id).isNotNull
        }


        @DisplayName("id 가 있는 게시판을 저장하고 조회하면 기존 게시판 정보를 덮어쓴 채 조회된다.")
        @Test
        fun test2() {
            // given
            val savedBoard = boardStorageFixture.save(
                boardFixtureNotRegistered(
                    name = "음식"
                )
            )

            val changedBoard = boardStorageFixture.save(
                boardFixtureRegistered(
                    id = savedBoard.id!!.value,
                    name = "요리"
                )
            )

            // when
            val findBoard = boardStorageFixture.findByIdOrNull(savedBoard.id!!)!!

            // then
            assertThat(findBoard.id).isEqualTo(savedBoard.id)
            assertThat(findBoard.id).isEqualTo(changedBoard.id)
            assertThat(findBoard.name.value).isEqualTo(changedBoard.name.value)
        }

    }


    @DisplayName("FindByIdOrNull: 식별자로 게시판을 조회할 수 있다.")
    @Nested
    inner class FindByIdOrNull {

        @Test
        @DisplayName("식별자로 게시판을 조회할 수 있다")
        fun findSuccessTest() {
            // given
            val board = boardStorageFixture.save(boardFixtureNotRegistered())

            // when
            val findBoard = boardStorageFixture.findByIdOrNull(board.id!!)!!

            // then
            assertThat(findBoard.id).isNotNull
            assertThat(findBoard.id).isEqualTo(board.id)
            assertThat(findBoard.name).isEqualTo(board.name)
            assertThat(findBoard.description).isEqualTo(board.description)
            assertThat(findBoard.managerId).isEqualTo(board.managerId)
            assertThat(findBoard.slug).isEqualTo(board.slug)
            assertThat(findBoard.createdAt).isEqualTo(board.createdAt)
        }

        @Test
        @DisplayName("못 찾으면 Null 반환됨")
        fun findNullTest() {
            // given
            val boardId = boardIdFixture(1557L)

            // when
            val board = boardStorageFixture.findByIdOrNull(boardId)

            // then
            assertThat(board).isNull()
        }
    }

    @Nested
    @DisplayName("ExistsByName : 이름으로 게시판 존재여부 확인")
    inner class ExistsByName {


        @Test
        @DisplayName("name 에 해당하는 게시판이 존재하면 true 반환")
        fun test1() {
            // given
            val savedBoard = boardStorageFixture.save(
                boardFixtureNotRegistered(
                    name = "음식"
                )
            )

            // when
            val exists = boardStorageFixture.existsByName(savedBoard.name)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("name 에 해당하는 게시판이 존재하지 않으면 false 반환")
        fun test2() {
            // given
            // when
            val exists = boardStorageFixture.existsByName(boardNameFixture("음식"))
            // then
            assertThat(exists).isFalse()
        }
    }

    @Nested
    @DisplayName("ExistsBySlug : 슬러그로 게시판 존재여부 확인")
    inner class ExistsBySlug {


        @Test
        @DisplayName("슬러그에 해당하는 게시판이 존재하면 true 반환")
        fun test1() {
            // given
            val savedBoard = boardStorageFixture.save(
                boardFixtureNotRegistered(
                    slug = "food"
                )
            )

            // when
            val exists = boardStorageFixture.existsBySlug(savedBoard.slug)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("슬러그에 해당하는 게시판이 존재하지 않으면 false 반환")
        fun test2() {
            // given
            // when
            val exists = boardStorageFixture.existsBySlug(boardSlugFixture("food"))
            // then
            assertThat(exists).isFalse()
        }
    }


    @DisplayName("FindBySlugOrNull: 슬러그로 게시판을 조회할 수 있다.")
    @Nested
    inner class FindBySlugOrNull {

        @Test
        @DisplayName("슬러그로 게시판을 조회할 수 있다")
        fun findSuccessTest() {
            // given
            val board = boardStorageFixture.save(
                boardFixtureNotRegistered(
                    slug = "food"
                )
            )

            // when
            val findBoard = boardStorageFixture.findBySlugOrNull(board.slug)!!

            // then
            assertThat(findBoard.id).isNotNull
            assertThat(findBoard.id).isEqualTo(board.id)
            assertThat(findBoard.name).isEqualTo(board.name)
            assertThat(findBoard.description).isEqualTo(board.description)
            assertThat(findBoard.managerId).isEqualTo(board.managerId)
            assertThat(findBoard.slug).isEqualTo(board.slug)
            assertThat(findBoard.createdAt).isEqualTo(board.createdAt)
        }

        @Test
        @DisplayName("못 찾으면 Null 반환됨")
        fun findNullTest() {
            // given
            val slug = boardSlugFixture("food")

            // when
            val board = boardStorageFixture.findBySlugOrNull(slug)

            // then
            assertThat(board).isNull()
        }
    }
}
