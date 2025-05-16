package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentInfiniteScrollReadQuery
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-application-service] ArticleCommentInfiniteScrollReadProcessor 테스트")
class ArticleCommentInfiniteScrollReadProcessorTest {

    private lateinit var processor: ArticleCommentInfiniteScrollReadProcessor
    private lateinit var articleCommentPersistencePortFixture: ArticleCommentPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.articleCommentInfiniteScrollReadProcessor
        articleCommentPersistencePortFixture = container.articleCommentPersistencePortFixture
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 있는 경우")
    fun test1() {
        // given
        val articleId = 1234566L

        // 1.. 4 (루트 댓글)
        for (i in 1..4) {
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
                    deleteStatus = ArticleCommentDeleteStatus.DELETED_BY_WRITER,
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

        //
        // 1
        // └ 51
        // └ 52
        // └ 53
        // 2
        // └ 54
        // ------------------------------------
        // └ 55 (삭제됨)
        // └ 56
        // 3
        // └ 57
        // └ 58 (삭제됨)
        // └ 59
        // ------------------------------- 끝
        // 4
        //

        val query = ArticleCommentInfiniteScrollReadQuery(
            articleId = articleId,
            pageSize = 6,
            lastRootParentCommentId = 2L,
            lastCommentId = 54L,
        )

        // when
        val response = processor.readAllInfiniteScroll(query)

        // then
        val commentIds = response.comments.mapNotNull { it.data?.articleCommentId }
        val deleteStatuses = response.comments.map { it.deleteStatus }
        val hasNext = response.hasNext

        assertThat(commentIds).containsExactly("56", "3", "57", "59")
        assertThat(deleteStatuses).containsExactly(
            ArticleCommentDeleteStatus.DELETED_BY_WRITER.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
            ArticleCommentDeleteStatus.DELETED_BY_WRITER.name,
            ArticleCommentDeleteStatus.NOT_DELETED.name,
        )
        assertThat(hasNext).isTrue()
    }

    @Test
    @DisplayName("성공 - 다음 페이지가 없는 경우")
    fun test2() {
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

        val query = ArticleCommentInfiniteScrollReadQuery(
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
        val response = processor.readAllInfiniteScroll(query)

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
