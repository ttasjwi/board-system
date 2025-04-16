package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ArticlePersistencePortFixtureTest {

    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        articlePersistencePortFixture = ArticlePersistencePortFixture()
    }

    @Nested
    @DisplayName("save & find : 저장 후 조회 테스트")
    inner class SaveAndFindTest {

        @Test
        @DisplayName("최초 저장 후 잘 조회되는 지 테스트")
        fun testCreate() {
            val articleId = 14578L
            val title = "title"
            val content = "content"
            val boardId = 1234556L
            val articleCategoryId = 2314558888L
            val writerId = 334L
            val createdAt = appDateTimeFixture(minute = 13)
            val modifiedAt = appDateTimeFixture(minute = 13)

            val article = articleFixture(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = writerId,
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            )

            // when
            articlePersistencePortFixture.save(article)
            val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(articleId)
            assertThat(findArticle.title).isEqualTo(title)
            assertThat(findArticle.content).isEqualTo(content)
            assertThat(findArticle.boardId).isEqualTo(boardId)
            assertThat(findArticle.articleCategoryId).isEqualTo(articleCategoryId)
            assertThat(findArticle.writerId).isEqualTo(writerId)
            assertThat(findArticle.createdAt).isEqualTo(createdAt)
            assertThat(findArticle.modifiedAt).isEqualTo(modifiedAt)
        }


        @Test
        @DisplayName("수정한 결과물을 저장 시, 수정한 결과물 상태로 변경되는 지 테스트")
        fun testModified() {
            // given
            val article = articleFixture(
                articleId = 14578L,
                title = "title",
                content = "content",
                boardId = 1234556L,
                articleCategoryId = 2314558888L,
                writerId = 334L,
                createdAt = appDateTimeFixture(minute = 13),
                modifiedAt = appDateTimeFixture(minute = 43),
            )
            articlePersistencePortFixture.save(article)


            val modifiedArticle = articleFixture(
                articleId = article.articleId,
                title = "modified_title",
                content = "modified_content",
                boardId = article.boardId,
                articleCategoryId = article.articleCategoryId,
                writerId = article.writerId,
                createdAt = article.createdAt,
                modifiedAt = appDateTimeFixture(minute = 43),
            )
            articlePersistencePortFixture.save(modifiedArticle)

            val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(article.articleId)
            assertThat(findArticle.title).isEqualTo(modifiedArticle.title)
            assertThat(findArticle.content).isEqualTo(modifiedArticle.content)
            assertThat(findArticle.boardId).isEqualTo(article.boardId)
            assertThat(findArticle.articleCategoryId).isEqualTo(article.articleCategoryId)
            assertThat(findArticle.createdAt).isEqualTo(article.createdAt)
            assertThat(findArticle.modifiedAt).isEqualTo(modifiedArticle.modifiedAt)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticle = articlePersistencePortFixture.findByIdOrNull(81233153L)
            assertThat(findArticle).isNull()
        }
    }
}
