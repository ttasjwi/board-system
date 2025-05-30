package com.ttasjwi.board.system.board.infra.persistence

import com.ttasjwi.board.system.board.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.test.DataBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[board-database-adapter] ArticleCategoryPersistenceAdapter 테스트")
class ArticleCategoryPersistenceAdapterTest : DataBaseIntegrationTest() {

    @Nested
    @DisplayName("save & find : 저장 후 조회 테스트")
    inner class SaveAndFindTest {

        @Test
        @DisplayName("최초 생성 시 잘 저장되는 지 테스트")
        fun testCreate() {
            // given
            val articleCategory = articleCategoryFixture(
                articleCategoryId = 123123677L,
                name = "일반",
                slug = "general",
                boardId = 1234567899L,
                allowWrite = true,
                allowSelfEditDelete = false,
                allowComment = false,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(hour = 12)
            )

            // when
            boardArticleCategoryPersistenceAdapter.save(articleCategory)
            flushAndClearEntityManager()
            val findArticleCategory =
                boardArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.boardId).isEqualTo(articleCategory.boardId)
            assertThat(findArticleCategory.name).isEqualTo(articleCategory.name)
            assertThat(findArticleCategory.slug).isEqualTo(articleCategory.slug)
            assertThat(findArticleCategory.allowWrite).isEqualTo(articleCategory.allowWrite)
            assertThat(findArticleCategory.allowSelfEditDelete).isEqualTo(articleCategory.allowSelfEditDelete)
            assertThat(findArticleCategory.allowComment).isEqualTo(articleCategory.allowComment)
            assertThat(findArticleCategory.allowLike).isEqualTo(articleCategory.allowLike)
            assertThat(findArticleCategory.allowDislike).isEqualTo(articleCategory.allowDislike)
            assertThat(findArticleCategory.createdAt).isEqualTo(articleCategory.createdAt)
        }


        @Test
        @DisplayName("수정한 결과물을 저장 시, 수정한 결과물 상태로 변경되는 지 테스트")
        fun testModified() {
            // given
            val articleCategory = articleCategoryFixture(
                articleCategoryId = 123123677L,
                name = "일반",
                slug = "general",
                boardId = 1234567899L,
                allowWrite = true,
                allowSelfEditDelete = false,
                allowComment = false,
                allowLike = true,
                allowDislike = true,
                createdAt = appDateTimeFixture(hour = 12)
            )
            boardArticleCategoryPersistenceAdapter.save(articleCategory)


            val modifiedArticleCategory = articleCategoryFixture(
                articleCategoryId = articleCategory.articleCategoryId,
                name = articleCategory.name,
                slug = articleCategory.slug,
                boardId = 1234567899L,
                allowWrite = false,
                allowSelfEditDelete = true,
                allowComment = true,
                allowLike = false,
                allowDislike = false,
                createdAt = articleCategory.createdAt,
            )
            boardArticleCategoryPersistenceAdapter.save(modifiedArticleCategory)

            flushAndClearEntityManager()
            val findArticleCategory =
                boardArticleCategoryPersistenceAdapter.findByIdOrNull(articleCategory.articleCategoryId)!!

            // then
            assertThat(findArticleCategory.articleCategoryId).isEqualTo(articleCategory.articleCategoryId)
            assertThat(findArticleCategory.boardId).isEqualTo(articleCategory.boardId)
            assertThat(findArticleCategory.name).isEqualTo(articleCategory.name)
            assertThat(findArticleCategory.slug).isEqualTo(articleCategory.slug)
            assertThat(findArticleCategory.allowWrite).isEqualTo(modifiedArticleCategory.allowWrite)
            assertThat(findArticleCategory.allowSelfEditDelete).isEqualTo(modifiedArticleCategory.allowSelfEditDelete)
            assertThat(findArticleCategory.allowComment).isEqualTo(modifiedArticleCategory.allowComment)
            assertThat(findArticleCategory.allowLike).isEqualTo(modifiedArticleCategory.allowLike)
            assertThat(findArticleCategory.allowDislike).isEqualTo(modifiedArticleCategory.allowDislike)
            assertThat(findArticleCategory.createdAt).isEqualTo(articleCategory.createdAt)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticleCategory = boardArticleCategoryPersistenceAdapter.findByIdOrNull(81233153L)

            assertThat(findArticleCategory).isNull()
        }
    }

    @Nested
    @DisplayName("existsByName : 게시글 카테고리 이름으로 존재 여부 확인")
    inner class ExistsByNameTest {

        @Test
        @DisplayName("boardId, articleCategoryName 이 일치하는 게시글 카테고리가 있으면 true 반환")
        fun trueTest() {
            // given
            val articleCategory = boardArticleCategoryPersistenceAdapter.save(
                articleCategoryFixture(
                    articleCategoryId = 123123677L,
                    name = "일반",
                    slug = "general",
                    boardId = 1234567899L,
                    allowWrite = true,
                    allowSelfEditDelete = false,
                    allowComment = true,
                    allowLike = true,
                    allowDislike = true,
                    createdAt = appDateTimeFixture(hour = 12)
                )
            )
            flushAndClearEntityManager()

            // when
            val exists = boardArticleCategoryPersistenceAdapter.existsByName(
                boardId = articleCategory.boardId,
                articleCategoryName = articleCategory.name
            )
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("boardId, articleCategoryName 이 일치하는 게시글 카테고리가 없으면 false 반환")
        fun falseTest() {
            // given
            // when
            val exists = boardArticleCategoryPersistenceAdapter.existsByName(
                boardId = 5555568L,
                articleCategoryName = "고양이"
            )

            // then
            assertThat(exists).isFalse()
        }
    }

    @Nested
    @DisplayName("existsBySlug : 게시글 카테고리 슬러그로 존재 여부 확인")
    inner class ExistsBySlugTest {

        @Test
        @DisplayName("boardId, articleCategorySlug 가 일치하는 게시글 카테고리가 있으면 true 반환")
        fun trueTest() {
            // given
            val articleCategory = boardArticleCategoryPersistenceAdapter.save(
                articleCategoryFixture(
                    articleCategoryId = 123123677L,
                    name = "일반",
                    slug = "general",
                    boardId = 1234567899L,
                    allowWrite = true,
                    allowSelfEditDelete = false,
                    allowComment = true,
                    allowLike = true,
                    allowDislike = true,
                    createdAt = appDateTimeFixture(hour = 12)
                )
            )
            flushAndClearEntityManager()

            // when
            val exists = boardArticleCategoryPersistenceAdapter.existsBySlug(
                boardId = articleCategory.boardId,
                articleCategorySlug = articleCategory.slug
            )
            assertThat(exists).isTrue()
        }


        @Test
        @DisplayName("boardId, articleCategorySlug 가 일치하는 게시글 카테고리가 없으면 false 반환")
        fun falseTest() {
            // given
            // when
            val exists = boardArticleCategoryPersistenceAdapter.existsBySlug(
                boardId = 5555568L,
                articleCategorySlug = "cat"
            )

            // then
            assertThat(exists).isFalse()
        }
    }
}
