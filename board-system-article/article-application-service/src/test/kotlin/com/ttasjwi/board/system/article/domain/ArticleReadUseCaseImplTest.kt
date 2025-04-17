package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.article.domain.model.fixture.articleFixture
import com.ttasjwi.board.system.article.domain.port.fixture.ArticlePersistencePortFixture
import com.ttasjwi.board.system.article.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[article-application-service] ArticleReadUseCaseImpl : 게시글 조회 유즈케이스 구현체")
class ArticleReadUseCaseImplTest {

    private lateinit var articleReadUseCase: ArticleReadUseCase
    private lateinit var articlePersistencePortFixture: ArticlePersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleReadUseCase = container.articleReadUseCase
        articlePersistencePortFixture = container.articlePersistencePortFixture
    }

    @Test
    @DisplayName("성공 테스트")
    fun successTest() {
        // given
        val article = articlePersistencePortFixture.save(articleFixture())
        val articleId = article.articleId

        // when
        val response = articleReadUseCase.read(articleId)

        // then
        assertThat(response.articleId).isEqualTo(articleId.toString())
        assertThat(response.title).isEqualTo(article.title)
        assertThat(response.content).isEqualTo(article.content)
        assertThat(response.boardId).isEqualTo(article.boardId.toString())
        assertThat(response.articleCategoryId).isEqualTo(article.articleCategoryId.toString())
        assertThat(response.writerId).isEqualTo(article.writerId.toString())
        assertThat(response.writerNickname).isEqualTo(article.writerNickname)
        assertThat(response.createdAt).isEqualTo(article.createdAt.toZonedDateTime())
        assertThat(response.modifiedAt).isEqualTo(article.modifiedAt.toZonedDateTime())
    }

    @Test
    @DisplayName("조회 실패 테스트")
    fun articleNotFoundTest() {
        // given
        val articleId = 5858959595L

        // when
        val ex = assertThrows<ArticleNotFoundException> { articleReadUseCase.read(articleId) }

        // then
        assertThat(ex.debugMessage).isEqualTo("조건에 부합하는 게시글을 찾지 못 했습니다. (articleId = $articleId)")
    }
}
