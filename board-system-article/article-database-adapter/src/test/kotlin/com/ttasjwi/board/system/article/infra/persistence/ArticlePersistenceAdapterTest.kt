package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.infra.test.ArticleDataBaseIntegrationTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-database-adapter] ArticlePersistenceAdapter 테스트")
class ArticlePersistenceAdapterTest : ArticleDataBaseIntegrationTest() {

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
            val writerNickname = "귀여운작성자"
            val createdAt = appDateTimeFixture(minute = 13)
            val modifiedAt = appDateTimeFixture(minute = 13)

            val article = articleFixture(
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

            // when
            articlePersistenceAdapter.save(article)
            flushAndClearEntityManager()
            val findArticle = articlePersistenceAdapter.findByIdOrNull(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(articleId)
            assertThat(findArticle.title).isEqualTo(title)
            assertThat(findArticle.content).isEqualTo(content)
            assertThat(findArticle.boardId).isEqualTo(boardId)
            assertThat(findArticle.articleCategoryId).isEqualTo(articleCategoryId)
            assertThat(findArticle.writerId).isEqualTo(writerId)
            assertThat(findArticle.writerNickname).isEqualTo(writerNickname)
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
                writerNickname = "멋쟁이작성자",
                createdAt = appDateTimeFixture(minute = 13),
                modifiedAt = appDateTimeFixture(minute = 43),
            )
            articlePersistenceAdapter.save(article)


            val modifiedArticle = articleFixture(
                articleId = article.articleId,
                title = "modified_title",
                content = "modified_content",
                boardId = article.boardId,
                articleCategoryId = article.articleCategoryId,
                writerId = article.writerId,
                writerNickname = article.writerNickname,
                createdAt = article.createdAt,
                modifiedAt = appDateTimeFixture(minute = 43),
            )
            articlePersistenceAdapter.save(modifiedArticle)

            flushAndClearEntityManager()
            val findArticle = articlePersistenceAdapter.findByIdOrNull(article.articleId)!!

            // then
            assertThat(findArticle.articleId).isEqualTo(article.articleId)
            assertThat(findArticle.title).isEqualTo(modifiedArticle.title)
            assertThat(findArticle.content).isEqualTo(modifiedArticle.content)
            assertThat(findArticle.boardId).isEqualTo(article.boardId)
            assertThat(findArticle.articleCategoryId).isEqualTo(article.articleCategoryId)
            assertThat(findArticle.writerId).isEqualTo(article.writerId)
            assertThat(findArticle.writerNickname).isEqualTo(article.writerNickname)
            assertThat(findArticle.createdAt).isEqualTo(article.createdAt)
            assertThat(findArticle.modifiedAt).isEqualTo(modifiedArticle.modifiedAt)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findArticle = articlePersistenceAdapter.findByIdOrNull(81233153L)
            assertThat(findArticle).isNull()
        }
    }

    @Nested
    @DisplayName("findAllPage: OffSet부터 시작하여, pageSize 만큼의 게시글 정보를 가져온다.")
    inner class FindAllPageTest {

        @Test
        @DisplayName("")
        fun test() {
            // given
            val boardId = 1234566L

            for (i in 1..10) {
                val article = articleFixture(
                    articleId = i.toLong(),
                    boardId = boardId,
                    title = "title-$i",
                )
                articlePersistenceAdapter.save(article)
            }
            // 10 9 8 / 7 6 ... 1
            // OffSet 을 3 부터 잡고, pageSize 를 3으로 잡기
            // 7, 6, 5
            val articles = articlePersistenceAdapter.findAllPage(
                boardId = boardId,
                offSet = 3,
                pageSize = 3,
            )
            val articleIds = articles.map { it.articleId }

            assertThat(articleIds.size).isEqualTo(3)
            assertThat(articleIds).containsExactly(7, 6, 5)
        }

    }

    @Nested
    @DisplayName("count: 최대 limit 건까지 범위 내에서 게시판 게시글의 갯수를 센다.")
    inner class CountTest {


        @Test
        @DisplayName("게시글 갯수가 limit 보다 같거나, 많으면, limit 만큼 갯수를 센다.")
        fun test1() {
            // given
            val boardId = 1234566L
            for (i in 1..10) {
                val article = articleFixture(
                    articleId = i.toLong(),
                    boardId = boardId,
                    title = "title-$i",
                )
                articlePersistenceAdapter.save(article)
            }

            // when
            val count = articlePersistenceAdapter.count(boardId, 9)

            // then
            assertThat(count).isEqualTo(9)
        }


        @Test
        @DisplayName("게시글 갯수가 limit 보다 적으면 게시글 갯수까지만큼 센다.")
        fun test2() {
            // given
            val boardId = 1234566L
            for (i in 1..10) {
                val article = articleFixture(
                    articleId = i.toLong(),
                    boardId = boardId,
                    title = "title-$i",
                )
                articlePersistenceAdapter.save(article)
            }

            // when
            val count = articlePersistenceAdapter.count(boardId, 15)

            // then
            assertThat(count).isEqualTo(10)
        }
    }
}
