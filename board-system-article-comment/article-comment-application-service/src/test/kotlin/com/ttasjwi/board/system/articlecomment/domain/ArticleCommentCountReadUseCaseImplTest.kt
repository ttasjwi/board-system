package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticleCommentCountPersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articlecomment.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-comment-application-service] ArticleCommentCountReadUseCaseImpl 테스트")
class ArticleCommentCountReadUseCaseImplTest {

    private lateinit var articleCommentCountReadUseCase: ArticleCommentCountReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleCommentCountPersistencePortFixture: ArticleCommentCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleCommentCountReadUseCase = container.articleCommentCountReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleCommentCountPersistencePortFixture = container.articleCommentCountPersistencePortFixture
    }


    @Test
    @DisplayName("성공 - 댓글수가 있을 경우")
    fun testSuccess1() {
        // given
        val articleId = 13L
        articlePersistencePortFixture.save(
            articleFixture(
                articleId = articleId,
                writerId = 155L,
                articleCategoryId = 143215L,
            )
        )

        articleCommentCountPersistencePortFixture.increase(articleId)
        articleCommentCountPersistencePortFixture.increase(articleId)
        articleCommentCountPersistencePortFixture.increase(articleId)
        articleCommentCountPersistencePortFixture.increase(articleId)

        // when
        val response = articleCommentCountReadUseCase.readCommentCount(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.commentCount).isEqualTo(4L)
    }

    @Test
    @DisplayName("성공 - 댓글수가 저장되어 있지 않은 경우 댓글수는 0이다.")
    fun testSuccess2() {
        // given
        val articleId = 13L
        articlePersistencePortFixture.save(
            articleFixture(
                articleId = articleId,
                writerId = 155L,
                articleCategoryId = 143215L,
            )
        )

        // when
        val response = articleCommentCountReadUseCase.readCommentCount(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.commentCount).isEqualTo(0L)
    }


    @Test
    @DisplayName("게시글이 저장되어 있지 않은 경우 예외 발생")
    fun testArticleNotFound() {
        // given
        val articleId = 14443L

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            articleCommentCountReadUseCase.readCommentCount(articleId)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
