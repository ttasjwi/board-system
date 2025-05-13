package com.ttasjwi.board.system.articlecomment.domain.model

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] Article 테스트")
class ArticleTest {

    @Test
    @DisplayName("restore : 값으로 부터 게시글 복원")
    fun restoreTest() {
        // given
        val articleId = 1234L
        val writerId = 1333515L
        val articleCategoryId = 12444444L

        // when
        val article = Article.restore(
            articleId = articleId,
            writerId = writerId,
            articleCategoryId = articleCategoryId
        )

        // then
        assertThat(article.articleId).isEqualTo(articleId)
        assertThat(article.writerId).isEqualTo(writerId)
        assertThat(article.articleCategoryId).isEqualTo(articleCategoryId)
    }


    @Test
    @DisplayName("toString(): 디버깅 문자열 반환")
    fun toStringTest() {
        // given
        val articleId = 1234L
        val writerId = 1333515L
        val articleCategoryId = 12444444L

        // when
        val article = articleFixture(
            articleId = articleId,
            writerId = writerId,
            articleCategoryId = articleCategoryId,
        )

        // then
        assertThat(article.toString()).isEqualTo("Article(articleId=$articleId, writerId=$writerId, articleCategoryId=$articleCategoryId)")
    }
}
