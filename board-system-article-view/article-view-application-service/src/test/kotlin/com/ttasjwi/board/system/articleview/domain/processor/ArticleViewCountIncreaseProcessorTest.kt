package com.ttasjwi.board.system.articleview.domain.processor

import com.ttasjwi.board.system.articleview.domain.command.ArticleViewCountIncreaseCommand
import com.ttasjwi.board.system.articleview.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountLockPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.port.fixture.ArticleViewCountPersistencePortFixture
import com.ttasjwi.board.system.articleview.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-view-application-service] ArticleViewCountIncreaseProcessor 테스트")
class ArticleViewCountIncreaseProcessorTest {

    private lateinit var articleViewCountIncreaseProcessor: ArticleViewCountIncreaseProcessor

    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    private lateinit var articleViewCountPersistencePortFixture: ArticleViewCountPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleViewCountIncreaseProcessor = container.articleViewCountIncreaseProcessor
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleViewCountPersistencePortFixture = container.articleViewCountPersistencePortFixture
    }


    @Test
    @DisplayName("성공 - 락 획득 성공한 경우")
    fun testSuccess1() {
        // given
        val articleId = 13L
        val user = authUserFixture(userId= 12345L, role = Role.USER)

        articlePersistencePortFixture.save(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)

        val command = ArticleViewCountIncreaseCommand(
            articleId = articleId,
            user = user,
        )

        // when
        articleViewCountIncreaseProcessor.increase(command)

        // then
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)!!
        assertThat(articleViewCount.articleId).isEqualTo(articleId)
        assertThat(articleViewCount.viewCount).isEqualTo(2L)
    }

    @Test
    @DisplayName("성공 - 락 획득 실패한 경우")
    fun testSuccess2() {
        // given
        val articleId = 13L
        val user = authUserFixture(userId= ArticleViewCountLockPersistencePortFixture.LOCK_USER_ID, role = Role.USER)
        articlePersistencePortFixture.save(articleId)
        articleViewCountPersistencePortFixture.increase(articleId)

        val command = ArticleViewCountIncreaseCommand(
            articleId = articleId,
            user = user,
        )

        // when
        articleViewCountIncreaseProcessor.increase(command)

        // then
        val articleViewCount = articleViewCountPersistencePortFixture.findByIdOrNull(articleId)!!
        assertThat(articleViewCount.viewCount).isEqualTo(1L)
    }


    @Test
    @DisplayName("게시글이 존재하지 않는다면 예외 발생")
    fun testArticleNotFound() {
        // given
        val articleId = 13L
        val user = authUserFixture(userId= 12345L, role = Role.USER)

        val command = ArticleViewCountIncreaseCommand(
            articleId = articleId,
            user = user,
        )

        val exception = assertThrows<ArticleNotFoundException> {
            articleViewCountIncreaseProcessor.increase(command)
        }

        assertThat(exception.args).containsExactly("articleId", articleId)
    }
}
