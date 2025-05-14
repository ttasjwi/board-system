package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentPageReadQuery
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("[article-comment-application-service] ArticleCommentPageReadProcessor 테스트")
class ArticleCommentPageReadProcessorTest {

    private lateinit var processor: ArticleCommentPageReadProcessor
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleCommentPageReadProcessor
        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
    }

    @Test
    @DisplayName("성공테스트")
    fun testSuccess() {
        // given
        val articleId = 1234566L

        for (i in 1..100) {
            val commentId = i.toLong()
            val comment = if (commentId <= 50L) {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId,
                    articleId = articleId,
                    deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
                )
            } else {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId - 50L,
                    articleId = articleId,
                    deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
                )
            }
            articleCommentPersistencePortFixture.save(comment)
        }

        val query = ArticleCommentPageReadQuery(
            articleId = articleId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = processor.readAllPage(query)
        val articleCommentIds = response.comments.map { it.data!!.articleCommentId }
        val deleteStatuses = response.comments.map { it.deleteStatus }.toSet()

        assertThat(response.page).isEqualTo(query.page)
        assertThat(response.pageSize).isEqualTo(query.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticleCommentPageReadProcessor.ARTICLE_COMMENT_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.comments).hasSize(query.pageSize.toInt())

        // 1 51 2 / 52 3 53 / ...
        assertThat(articleCommentIds).containsExactly("52", "3", "53")
        assertThat(deleteStatuses).containsExactly(ArticleCommentDeleteStatus.NOT_DELETED.name)
    }


    @Test
    @DisplayName("삭제된 댓글은 노출되지 않는다.")
    fun testDeleted() {
        // given
        val articleId = 1234566L

        for (i in 1..100) {
            val commentId = i.toLong()
            val comment = if (commentId <= 50L) {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId,
                    articleId = articleId,
                    deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER
                )
            } else {
                articleCommentFixture(
                    articleCommentId = commentId,
                    rootParentCommentId = commentId - 50L,
                    articleId = articleId,
                    deleteStatus = ArticleCommentDeleteStatus.NOT_DELETED
                )
            }
            articleCommentPersistencePortFixture.save(comment)
        }

        val query = ArticleCommentPageReadQuery(
            articleId = articleId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = processor.readAllPage(query)
        val articleCommentIds = response.comments.mapNotNull {
            it.data?.articleCommentId
        }
        val deleteStatuses = response.comments.map { it.deleteStatus }

        assertThat(response.page).isEqualTo(query.page)
        assertThat(response.pageSize).isEqualTo(query.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticleCommentPageReadProcessor.ARTICLE_COMMENT_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.comments).hasSize(query.pageSize.toInt())

        // 1 51 2 / [52 3(삭제됨) 53] / ...
        assertThat(articleCommentIds).containsExactly("52", "53")
        assertThat(deleteStatuses).containsExactly(
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.DELETED_BY_WRITER.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name
        )
    }
}
