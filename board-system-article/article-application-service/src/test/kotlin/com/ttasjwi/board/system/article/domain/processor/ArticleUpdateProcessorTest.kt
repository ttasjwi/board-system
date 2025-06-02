package com.ttasjwi.board.system.article.domain.processor

import com.ttasjwi.board.system.article.domain.dto.ArticleUpdateCommand
import com.ttasjwi.board.system.article.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.article.domain.exception.ArticleUpdateNotAllowedByCategoryRuleException
import com.ttasjwi.board.system.article.domain.exception.ArticleUpdateNotAllowedByWriterMismatchException
import com.ttasjwi.board.system.article.domain.model.fixture.articleCategoryFixture
import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticleCategoryPersistencePortFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticleUpdateProcessor 테스트")
class ArticleUpdateProcessorTest {

    private lateinit var processor: ArticleUpdateProcessor
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture
    private lateinit var articleCategoryPersistencePortFixture: ArticleCategoryPersistencePortFixture

    @BeforeEach
    fun setUp() {
        val container = TestContainer.create()
        processor = container.articleUpdateProcessor
        articlePersistencePortFixture = container.articlePersistencePortFixture
        articleCategoryPersistencePortFixture = container.articleCategoryPersistencePortFixture
    }

    @Test
    @DisplayName("성공 - 게시글 제목/내용 변경 됐을 때")
    fun testSuccess1() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "new title"
        val newContent = "new content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = user.userId,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowSelfEditDelete = true
            )
        )

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = article.articleId,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val updatedArticle = processor.update(command)

        // then
        val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(updatedArticle.articleId).isEqualTo(command.articleId)
        assertThat(updatedArticle.title).isEqualTo(newTitle)
        assertThat(updatedArticle.content).isEqualTo(newContent)
        assertThat(updatedArticle.modifiedAt).isEqualTo(command.currentTime)

        assertThat(findArticle.articleId).isEqualTo(command.articleId)
        assertThat(findArticle.title).isEqualTo(updatedArticle.title)
        assertThat(findArticle.content).isEqualTo(updatedArticle.content)
        assertThat(findArticle.modifiedAt).isEqualTo(updatedArticle.modifiedAt)
    }

    @Test
    @DisplayName("성공 - 게시글 제목/내용 변경 안 됐을 때")
    fun testSuccess2() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "title"
        val newContent = "content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = user.userId,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowSelfEditDelete = true
            )
        )

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = article.articleId,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val updatedArticle = processor.update(command)

        // then
        val findArticle = articlePersistencePortFixture.findByIdOrNull(article.articleId)!!

        assertThat(updatedArticle.articleId).isEqualTo(command.articleId)
        assertThat(updatedArticle.title).isEqualTo(prevTitle)
        assertThat(updatedArticle.content).isEqualTo(prevContent)
        assertThat(updatedArticle.modifiedAt).isEqualTo(appDateTimeFixture(minute = 0))

        assertThat(findArticle.articleId).isEqualTo(command.articleId)
        assertThat(findArticle.title).isEqualTo(updatedArticle.title)
        assertThat(findArticle.content).isEqualTo(updatedArticle.content)
        assertThat(findArticle.modifiedAt).isEqualTo(updatedArticle.modifiedAt)
    }

    @Test
    @DisplayName("실패 - 게시글 조회 실패")
    fun testArticleNotFound() {
        // given
        val newTitle = "new title"
        val newContent = "new content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = 53413413L,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val exception = assertThrows<ArticleNotFoundException> {
            processor.update(command)
        }

        // then
        assertThat(exception.args).containsExactly("articleId", command.articleId)
    }

    @Test
    @DisplayName("실패 - 글 작성자가 아닌 사람이 수정할 때")
    fun testWriterMismatch() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "new title"
        val newContent = "new content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = 14141235L,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowSelfEditDelete = true
            )
        )

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = article.articleId,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val exception = assertThrows<ArticleUpdateNotAllowedByWriterMismatchException> {
            processor.update(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, command.authUser.userId)
    }

    @Test
    @DisplayName("실패 - 게시글 카테고리 조회 실패 (서버 에러)")
    fun testArticleCategoryNotFound() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "new title"
        val newContent = "new content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = user.userId,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = article.articleId,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val exception = assertThrows<IllegalStateException> {
            processor.update(command)
        }

        // then
        assertThat(exception.message).isEqualTo("게시글 카테고리 조회 실패! (articleId=${command.articleId}, articleCategoryId=${article.articleCategoryId})")
    }

    // 실패 : 게시글 카테고리 정책에 의해 게시글 수정이 불가능할 때
    @Test
    @DisplayName("실패 - 게시글 카테고리 정책에 의해 게시글 수정이 불가능할 때")
    fun testFailedCausedByArticleCategoryRule() {
        // given
        val prevTitle = "title"
        val prevContent = "content"

        val newTitle = "new title"
        val newContent = "new content"
        val user = authUserFixture(userId = 12345L, role = Role.USER)

        val article = articlePersistencePortFixture.save(
            articleFixture(
                title = prevTitle,
                content = prevContent,
                writerId = user.userId,
                createdAt = appDateTimeFixture(minute = 0),
                modifiedAt = appDateTimeFixture(minute = 0)
            )
        )

        articleCategoryPersistencePortFixture.save(
            articleCategoryFixture(
                articleCategoryId = article.articleCategoryId,
                allowSelfEditDelete = false
            )
        )

        val command = ArticleUpdateCommand(
            title = newTitle,
            content = newContent,
            articleId = article.articleId,
            authUser = user,
            currentTime = appDateTimeFixture(minute = 3),
        )

        // when
        val exception = assertThrows<ArticleUpdateNotAllowedByCategoryRuleException> {
            processor.update(command)
        }

        // then
        assertThat(exception.args).containsExactly(command.articleId, article.articleCategoryId)
    }

}
