package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-service] ArticleCommentInfiniteScrollReadUseCase 테스트")
class ArticleCommentInfiniteScrollReadUseCaseImplTest {

    private lateinit var useCase: ArticleCommentInfiniteScrollReadUseCase
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        useCase = container.articleCommentInfiniteScrollReadUseCase
        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun test1() {
        // given
        val articleId = 1234566L

        // 1.. 3 (루트 댓글)
        for (i in 1..3) {
            val articleCommentId = i.toLong()
            articleCommentPersistencePortFixture.save(
                articleCommentFixture(
                    articleCommentId = articleCommentId,
                    articleId = articleId,
                    rootParentCommentId = articleCommentId
                )
            )
        }


        // 51 52 53 54 55 56 57 58 59 (자식댓글)
        var subCommentId = 51L
        for (i in 1..3) {
            val rootParentCommentId = i.toLong()
            articleCommentPersistencePortFixture.save(
                articleCommentFixture(
                    articleCommentId = subCommentId++,
                    articleId = articleId,
                    rootParentCommentId = rootParentCommentId
                )
            )
            articleCommentPersistencePortFixture.save(
                articleCommentFixture(
                    articleCommentId = subCommentId++,
                    articleId = articleId,
                    rootParentCommentId = rootParentCommentId,
                )
            )
            articleCommentPersistencePortFixture.save(
                articleCommentFixture(
                    articleCommentId = subCommentId++,
                    articleId = articleId,
                    rootParentCommentId = rootParentCommentId,
                )
            )
        }

        val request = ArticleCommentInfiniteScrollReadRequest(
            articleId = articleId,
            pageSize = 6,
            lastRootParentCommentId = 2L,
            lastCommentId = 54L,
        )

        //
        // 1
        // └ 51
        // └ 52
        // └ 53
        // 2
        // └ 54
        // ------------------------------------
        // └ 55
        // └ 56
        // 3
        // └ 57
        // └ 58
        // └ 59
        // ------------------------------- 끝

        // when
        val response = useCase.readAllInfiniteScroll(request)

        // then
        val commentIds = response.comments.mapNotNull { it.data?.articleCommentId }
        val deleteStatuses = response.comments.map { it.deleteStatus }
        val hasNext = response.hasNext

        assertThat(commentIds).containsExactly("55", "56", "3", "57", "58", "59")
        assertThat(deleteStatuses).containsExactly(
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
        )
        assertThat(hasNext).isFalse()
    }

}
