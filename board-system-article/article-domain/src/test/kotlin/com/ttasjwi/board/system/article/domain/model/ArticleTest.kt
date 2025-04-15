package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Article: 게시글 테스트")
class ArticleTest {

    @Nested
    @DisplayName("create : 게시글을 생성한다.")
    inner class CreateTest {

        @Test
        @DisplayName("값을 통해 생성할 수 있다.")
        fun test() {
            // given
            val articleId = 14578L
            val title = "title"
            val content = "content"
            val boardId = 1234556L
            val boardArticleCategoryId = 2314558888L
            val writerId = 334L
            val createdAt = appDateTimeFixture(minute = 13)

            // when
            val article = Article.create(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                boardArticleCategoryId = boardArticleCategoryId,
                writerId = writerId,
                createdAt = createdAt,
            )

            // then
            assertThat(article.articleId).isEqualTo(articleId)
            assertThat(article.title).isEqualTo(title)
            assertThat(article.content).isEqualTo(content)
            assertThat(article.boardId).isEqualTo(boardId)
            assertThat(article.boardArticleCategoryId).isEqualTo(boardArticleCategoryId)
            assertThat(article.writerId).isEqualTo(writerId)
            assertThat(article.createdAt).isEqualTo(createdAt)
            assertThat(article.modifiedAt).isEqualTo(createdAt)
        }
    }

    @Nested
    @DisplayName("restore: 게시글을 복원한다.")
    inner class RestoreTest {


        @Test
        @DisplayName("값을 통해 복원할 수 있다.")
        fun test() {
            // given
            val articleId = 14578L
            val title = "title"
            val content = "content"
            val boardId = 1234556L
            val boardArticleCategoryId = 2314558888L
            val writerId = 334L
            val createdAt = appDateTimeFixture(minute = 13).toLocalDateTime()
            val modifiedAt = appDateTimeFixture(minute = 26).toLocalDateTime()

            // when
            val article = Article.restore(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                boardArticleCategoryId = boardArticleCategoryId,
                writerId = writerId,
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(article.articleId).isEqualTo(articleId)
            assertThat(article.title).isEqualTo(title)
            assertThat(article.content).isEqualTo(content)
            assertThat(article.boardId).isEqualTo(boardId)
            assertThat(article.boardArticleCategoryId).isEqualTo(boardArticleCategoryId)
            assertThat(article.writerId).isEqualTo(writerId)
            assertThat(article.createdAt.toLocalDateTime()).isEqualTo(createdAt)
            assertThat(article.modifiedAt.toLocalDateTime()).isEqualTo(modifiedAt)
        }
    }

    @Nested
    @DisplayName("toString(): 디버깅 문자열을 반환한다.")
    inner class ToStringTest {


        @Test
        @DisplayName("제대로 디버깅 문자열이 반환되는 지 테스트")
        fun toStringTest() {
            // given
            val articleId = 14578L
            val title = "title"
            val content = "content"
            val boardId = 1234556L
            val boardArticleCategoryId = 2314558888L
            val writerId = 334L
            val createdAt = appDateTimeFixture(minute = 13)
            val modifiedAt = appDateTimeFixture(minute = 43)

            val article = articleFixture(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                boardArticleCategoryId = boardArticleCategoryId,
                writerId = writerId,
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            )

            // when
            val string = article.toString()

            // then
            assertThat(string).isEqualTo("Article(articleId=$articleId, title='$title', content='$content', boardId=$boardId, boarArticleCategoryId=$boardArticleCategoryId, writerId=$writerId, createdAt=$createdAt, modifiedAt=$modifiedAt)")
        }
    }
}
