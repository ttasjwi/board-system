package com.ttasjwi.board.system.board.domain.external.db

import com.ttasjwi.board.system.IntegrationTest
import com.ttasjwi.board.system.domain.auth.model.boardFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

@Transactional
@DisplayName("BoardStorageImpl 테스트")
class BoardStorageImplTest : IntegrationTest() {

    @DisplayName("save 테스트")
    @Nested
    inner class Save {

        @DisplayName("저장되어 있는 게시판을 다시 저장하면, 상태가 변경된다.")
        @Test
        fun test2() {
            // given
            val savedBoard = boardStorageImpl.save(
                boardFixture(
                    id = 1234L,
                    name = "음식"
                )
            )

            val changedBoard = boardStorageImpl.save(
                boardFixture(
                    id = savedBoard.id,
                    name = "요리"
                )
            )
            flushAndClearEntityManager()

            // when
            val findBoard = boardStorageImpl.findByIdOrNull(savedBoard.id)!!

            // then
            assertThat(findBoard.id).isEqualTo(savedBoard.id)
            assertThat(findBoard.id).isEqualTo(changedBoard.id)
            assertThat(findBoard.name).isEqualTo(changedBoard.name)
        }

    }


    @DisplayName("FindByIdOrNull: 식별자로 게시판을 조회할 수 있다.")
    @Nested
    inner class FindByIdOrNull {

        @Test
        @DisplayName("식별자로 게시판을 조회할 수 있다")
        fun findSuccessTest() {
            // given
            val board = boardStorageImpl.save(
                boardFixture(
                    id = 12345677L,
                )
            )
            flushAndClearEntityManager()

            // when
            val findBoard = boardStorageImpl.findByIdOrNull(board.id)!!

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
            val boardId = 15788900L

            // when
            val board = boardStorageImpl.findByIdOrNull(boardId)

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
            val savedBoard = boardStorageImpl.save(
                boardFixture(
                    name = "음식"
                )
            )

            // when
            val exists = boardStorageImpl.existsByName(savedBoard.name)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("name 에 해당하는 게시판이 존재하지 않으면 false 반환")
        fun test2() {
            // given
            // when
            val exists = boardStorageImpl.existsByName("음식")
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
            val savedBoard = boardStorageImpl.save(
                boardFixture(
                    slug = "food"
                )
            )

            // when
            val exists = boardStorageImpl.existsBySlug(savedBoard.slug)

            // then
            assertThat(exists).isTrue()
        }

        @Test
        @DisplayName("슬러그에 해당하는 게시판이 존재하지 않으면 false 반환")
        fun test2() {
            // given
            // when
            val exists = boardStorageImpl.existsBySlug("food")
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
            val board = boardStorageImpl.save(
                boardFixture(
                    slug = "food"
                )
            )
            flushAndClearEntityManager()

            // when
            val findBoard = boardStorageImpl.findBySlugOrNull(board.slug)!!

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
            val slug = "food"

            // when
            val board = boardStorageImpl.findBySlugOrNull(slug)

            // then
            assertThat(board).isNull()
        }
    }

}
