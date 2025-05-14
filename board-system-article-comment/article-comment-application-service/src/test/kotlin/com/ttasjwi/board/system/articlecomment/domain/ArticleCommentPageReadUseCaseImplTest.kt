package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.processor.ArticleCommentPageReadProcessor
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("[article-comment-application-service] ArticleCommentPageReadUseCase 테스트")
class ArticleCommentPageReadUseCaseImplTest {

    private lateinit var useCase: ArticleCommentPageReadUseCase
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        useCase = container.articleCommentPageReadUseCase
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

        val request = ArticleCommentPageReadRequest(
            articleId = articleId,
            page = 2L,
            pageSize = 3L
        )

        // when
        val response = useCase.readAllPage(request)
        val articleCommentIds = response.comments.map { it.data!!.articleCommentId }
        val deleteStatuses = response.comments.map { it.deleteStatus }.toSet()

        assertThat(response.page).isEqualTo(request.page)
        assertThat(response.pageSize).isEqualTo(request.pageSize)
        assertThat(response.pageGroupSize).isEqualTo(ArticleCommentPageReadProcessor.ARTICLE_COMMENT_PAGE_GROUP_SIZE)
        assertThat(response.pageGroupStart).isEqualTo(1)
        assertThat(response.pageGroupEnd).isEqualTo(10L)
        assertThat(response.hasNextPage).isTrue()
        assertThat(response.hasNextPageGroup).isTrue()
        assertThat(response.comments).hasSize(request.pageSize!!.toInt())

        // 1 51 2 / 52 3 53 / ...
        assertThat(articleCommentIds).containsExactly("52", "3", "53")
        assertThat(deleteStatuses).containsExactly(ArticleCommentDeleteStatus.NOT_DELETED.name)
    }
}
