package com.ttasjwi.board.system.articlecomment.domain.model

import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentCountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentCount 테스트")
class ArticleCommentCountTest {

    @Test
    @DisplayName("restore : 값으로 부터 객체 복원")
    fun restoreTest() {
        // given
        val articleId = 134L
        val commentCount = 55L

        // when
        val articleCommentCount = ArticleCommentCount.restore(
            articleId = articleId,
            commentCount = commentCount
        )

        // then
        assertThat(articleCommentCount.articleId).isEqualTo(articleId)
        assertThat(articleCommentCount.commentCount).isEqualTo(commentCount)
    }


    @Test
    @DisplayName("toString() : 디버깅문자열 반환")
    fun toStringTest() {
        // given
        val articleId = 134L
        val commentCount = 55L

        // when
        val articleComment = articleCommentCountFixture(
            articleId = articleId,
            commentCount = commentCount
        )

        // then
        assertThat(articleComment.toString()).isEqualTo("ArticleCommentCount(articleId=$articleId, commentCount=$commentCount)")
    }
}
