package com.ttasjwi.board.system.article.domain.model

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] Article: 게시글 테스트")
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
            val articleCategoryId = 2314558888L
            val writerId = 334L
            val writerNickname = "깜찍이"
            val createdAt = appDateTimeFixture(minute = 13)

            // when
            val article = Article.create(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = writerId,
                writerNickname = writerNickname,
                createdAt = createdAt,
            )

            // then
            assertThat(article.articleId).isEqualTo(articleId)
            assertThat(article.title).isEqualTo(title)
            assertThat(article.content).isEqualTo(content)
            assertThat(article.boardId).isEqualTo(boardId)
            assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
            assertThat(article.writerId).isEqualTo(writerId)
            assertThat(article.writerNickname).isEqualTo(writerNickname)
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
            val articleCategoryId = 2314558888L
            val writerId = 334L
            val writerNickname = "작성자"
            val createdAt = appDateTimeFixture(minute = 13).toLocalDateTime()
            val modifiedAt = appDateTimeFixture(minute = 26).toLocalDateTime()

            // when
            val article = Article.restore(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerId = writerId,
                writerNickname = writerNickname,
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(article.articleId).isEqualTo(articleId)
            assertThat(article.title).isEqualTo(title)
            assertThat(article.content).isEqualTo(content)
            assertThat(article.boardId).isEqualTo(boardId)
            assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
            assertThat(article.writerId).isEqualTo(writerId)
            assertThat(article.writerNickname).isEqualTo(writerNickname)
            assertThat(article.createdAt.toLocalDateTime()).isEqualTo(createdAt)
            assertThat(article.modifiedAt.toLocalDateTime()).isEqualTo(modifiedAt)
        }
    }

    @Nested
    @DisplayName("isWriter: 작성자가 맞는지 확인한다.")
    inner class IsWriterTest {


        @Test
        @DisplayName("작성자가 맞으면 true 를 반환한다.")
        fun test1() {
            // given
            val article = articleFixture(writerId = 125L)
            val userId = article.writerId

            // when
            val isWriter = article.isWriter(userId)

            // then
            assertThat(isWriter).isTrue()
        }


        @Test
        @DisplayName("작성자가 아니면 false 를 반환한다.")
        fun test2() {
            // given
            val article = articleFixture(writerId = 125L)
            val userId = 1345135L

            // when
            val isWriter = article.isWriter(userId)

            // then
            assertThat(isWriter).isFalse()
        }
    }

    @Nested
    @DisplayName("update: 게시글을 수정한다.")
    inner class UpdateTest {

        @Test
        @DisplayName("제목, 본문의 내용 변경이 없으면 수정되지 않는다.")
        fun test1() {
            // given
            val prevTitle = "title"
            val prevContent = "content"
            val newTitle = "title"
            val newContent = "content"

            val article = articleFixture(
                title = prevTitle,
                content = prevContent,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0),
            )

            val modifiedAt = appDateTimeFixture(minute = 3)

            // when
            val updateResult = article.update(
                title = newTitle,
                content = newContent,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(updateResult).isEqualTo(Article.UpdateResult.UNCHANGED)
            assertThat(article.title).isEqualTo(prevTitle)
            assertThat(article.content).isEqualTo(prevContent)
            assertThat(article.modifiedAt).isEqualTo(article.modifiedAt)
        }

        @Test
        @DisplayName("제목이 달라지는 경우")
        fun test2() {
            // given
            val prevTitle = "title"
            val prevContent = "content"
            val newTitle = "title222"
            val newContent = "content"

            val article = articleFixture(
                title = prevTitle,
                content = prevContent,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0),
            )

            val modifiedAt = appDateTimeFixture(minute = 3)

            // when
            val updateResult = article.update(
                title = newTitle,
                content = newContent,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(updateResult).isEqualTo(Article.UpdateResult.CHANGED)
            assertThat(article.title).isEqualTo(newTitle)
            assertThat(article.content).isEqualTo(prevContent)
            assertThat(article.modifiedAt).isEqualTo(modifiedAt)
        }

        @Test
        @DisplayName("본문이 달라지는 경우")
        fun test3() {
            // given
            val prevTitle = "title"
            val prevContent = "content"
            val newTitle = "title"
            val newContent = "content222"

            val article = articleFixture(
                title = prevTitle,
                content = prevContent,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0),
            )

            val modifiedAt = appDateTimeFixture(minute = 3)

            // when
            val updateResult = article.update(
                title = newTitle,
                content = newContent,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(updateResult).isEqualTo(Article.UpdateResult.CHANGED)
            assertThat(article.title).isEqualTo(prevTitle)
            assertThat(article.content).isEqualTo(newContent)
            assertThat(article.modifiedAt).isEqualTo(modifiedAt)
        }

        @Test
        @DisplayName("제목, 본문이 모두 달라지는 경우")
        fun test4() {
            // given
            val prevTitle = "title"
            val prevContent = "content"
            val newTitle = "title222"
            val newContent = "content222"

            val article = articleFixture(
                title = prevTitle,
                content = prevContent,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0),
            )

            val modifiedAt = appDateTimeFixture(minute = 3)

            // when
            val updateResult = article.update(
                title = newTitle,
                content = newContent,
                modifiedAt = modifiedAt,
            )

            // then
            assertThat(updateResult).isEqualTo(Article.UpdateResult.CHANGED)
            assertThat(article.title).isEqualTo(newTitle)
            assertThat(article.content).isEqualTo(newContent)
            assertThat(article.modifiedAt).isEqualTo(modifiedAt)
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
            val articleCategoryId = 2314558888L
            val writerId = 334L
            val writerNickname = "땃쥐"
            val createdAt = appDateTimeFixture(minute = 13)
            val modifiedAt = appDateTimeFixture(minute = 43)

            val article = articleFixture(
                articleId = articleId,
                title = title,
                content = content,
                boardId = boardId,
                articleCategoryId = articleCategoryId,
                writerNickname = writerNickname,
                writerId = writerId,
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            )

            // when
            val string = article.toString()

            // then
            assertThat(string).isEqualTo("Article(articleId=$articleId, title='$title', content='$content', boardId=$boardId, articleCategoryId=$articleCategoryId, writerId=$writerId, writerNickname='$writerNickname', createdAt=$createdAt, modifiedAt=$modifiedAt)")
        }
    }
}
