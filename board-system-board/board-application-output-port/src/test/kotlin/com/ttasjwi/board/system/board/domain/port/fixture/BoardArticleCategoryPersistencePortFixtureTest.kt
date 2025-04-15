package com.ttasjwi.board.system.board.domain.port.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.boardArticleCategoryFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("BoardArticleCategoryPersistencePortFixture 테스트")
class BoardArticleCategoryPersistencePortFixtureTest {

    private lateinit var boardArticleCategoryPersistencePortFixture: BoardArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setup() {
        boardArticleCategoryPersistencePortFixture = BoardArticleCategoryPersistencePortFixture()
    }

    @Nested
    @DisplayName("save & find : 저장 후 조회 테스트")
    inner class SaveAndFindTest {

        @Test
        @DisplayName("최초 생성 시 잘 저장되는 지 테스트")
        fun testCreate() {
            // given
            val boardArticleCategory = boardArticleCategoryFixture(
                boardArticleCategoryId = 123123677L,
                name = "일반",
                slug = "general",
                boardId = 1234567899L,
                allowSelfDelete = false,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(hour = 12)
            )

            // when
            boardArticleCategoryPersistencePortFixture.save(boardArticleCategory)
            val findBoardArticleCategory =
                boardArticleCategoryPersistencePortFixture.findByIdOrNull(boardArticleCategory.boardArticleCategoryId)!!

            // then
            assertThat(findBoardArticleCategory.boardArticleCategoryId).isEqualTo(boardArticleCategory.boardArticleCategoryId)
            assertThat(findBoardArticleCategory.boardId).isEqualTo(boardArticleCategory.boardId)
            assertThat(findBoardArticleCategory.name).isEqualTo(boardArticleCategory.name)
            assertThat(findBoardArticleCategory.slug).isEqualTo(boardArticleCategory.slug)
            assertThat(findBoardArticleCategory.allowSelfDelete).isEqualTo(boardArticleCategory.allowSelfDelete)
            assertThat(findBoardArticleCategory.allowLike).isEqualTo(boardArticleCategory.allowLike)
            assertThat(findBoardArticleCategory.allowDislike).isEqualTo(boardArticleCategory.allowDislike)
            assertThat(findBoardArticleCategory.createdAt).isEqualTo(boardArticleCategory.createdAt)
        }


        @Test
        @DisplayName("수정한 결과물을 저장 시, 수정한 결과물 상태로 변경되는 지 테스트")
        fun testModified() {
            // given
            val boardArticleCategory = boardArticleCategoryFixture(
                boardArticleCategoryId = 123123677L,
                name = "일반",
                slug = "general",
                boardId = 1234567899L,
                allowSelfDelete = false,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(hour = 12)
            )
            boardArticleCategoryPersistencePortFixture.save(boardArticleCategory)


            val modifiedBoardArticleCategory = boardArticleCategoryFixture(
                boardArticleCategoryId = boardArticleCategory.boardArticleCategoryId,
                name = boardArticleCategory.name,
                slug = boardArticleCategory.slug,
                boardId = 1234567899L,
                allowSelfDelete = true,
                allowLike = false,
                allowDislike = false,
                createdAt = boardArticleCategory.createdAt,
            )
            boardArticleCategoryPersistencePortFixture.save(modifiedBoardArticleCategory)

            val findBoardArticleCategory = boardArticleCategoryPersistencePortFixture.findByIdOrNull(boardArticleCategory.boardArticleCategoryId)!!

            // then
            assertThat(findBoardArticleCategory.boardArticleCategoryId).isEqualTo(boardArticleCategory.boardArticleCategoryId)
            assertThat(findBoardArticleCategory.boardId).isEqualTo(boardArticleCategory.boardId)
            assertThat(findBoardArticleCategory.name).isEqualTo(boardArticleCategory.name)
            assertThat(findBoardArticleCategory.slug).isEqualTo(boardArticleCategory.slug)
            assertThat(findBoardArticleCategory.allowSelfDelete).isEqualTo(modifiedBoardArticleCategory.allowSelfDelete)
            assertThat(findBoardArticleCategory.allowLike).isEqualTo(modifiedBoardArticleCategory.allowLike)
            assertThat(findBoardArticleCategory.allowDislike).isEqualTo(modifiedBoardArticleCategory.allowDislike)
            assertThat(findBoardArticleCategory.createdAt).isEqualTo(boardArticleCategory.createdAt)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findBoardArticleCategory = boardArticleCategoryPersistencePortFixture.findByIdOrNull(81233153L)

            assertThat(findBoardArticleCategory).isNull()
        }
    }

    @Nested
    @DisplayName("existsByName : 게시글 카테고리 이름으로 존재 여부 확인")
    inner class ExistsByNameTest {

        @Test
        @DisplayName("boardId, boardArticleCategoryName 이 일치하는 게시글 카테고리가 있으면 true 반환")
        fun trueTest() {
            // given
            val boardArticleCategory = boardArticleCategoryPersistencePortFixture.save(
                boardArticleCategoryFixture(
                    boardArticleCategoryId = 123123677L,
                    name = "일반",
                    slug = "general",
                    boardId = 1234567899L,
                    allowSelfDelete = false,
                    allowLike = true,
                    allowDislike = true,
                    createdAt = appDateTimeFixture(hour = 12)
                )
            )

            // when
            val exists = boardArticleCategoryPersistencePortFixture.existsByName(
                boardId = boardArticleCategory.boardId,
                boardArticleCategoryName = boardArticleCategory.name
            )
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("boardId, boardArticleCategoryName 이 일치하는 게시글 카테고리가 없으면 false 반환")
        fun falseTest() {
            // given
            // when
            val exists = boardArticleCategoryPersistencePortFixture.existsByName(
                boardId = 5555568L,
                boardArticleCategoryName = "고양이"
            )

            // then
            assertThat(exists).isFalse()
        }
    }

    @Nested
    @DisplayName("existsBySlug : 게시글 카테고리 슬러그로 존재 여부 확인")
    inner class ExistsBySlugTest {

        @Test
        @DisplayName("boardId, boardArticleCategorySlug 가 일치하는 게시글 카테고리가 있으면 true 반환")
        fun trueTest() {
            // given
            val boardArticleCategory = boardArticleCategoryPersistencePortFixture.save(
                boardArticleCategoryFixture(
                    boardArticleCategoryId = 123123677L,
                    name = "일반",
                    slug = "general",
                    boardId = 1234567899L,
                    allowSelfDelete = false,
                    allowLike = true,
                    allowDislike = true,
                    createdAt = appDateTimeFixture(hour = 12)
                )
            )

            // when
            val exists = boardArticleCategoryPersistencePortFixture.existsBySlug(
                boardId = boardArticleCategory.boardId,
                boardArticleCategorySlug = boardArticleCategory.slug
            )
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("boardId, boardArticleCategorySlug 가 일치하는 게시글 카테고리가 없으면 false 반환")
        fun falseTest() {
            // given
            // when
            val exists = boardArticleCategoryPersistencePortFixture.existsBySlug(
                boardId = 5555568L,
                boardArticleCategorySlug = "cat"
            )

            // then
            assertThat(exists).isFalse()
        }
    }
}
