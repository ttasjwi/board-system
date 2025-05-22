package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentCountFixture 테스트")
class ArticleCommentCountFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val articleCommentCount = articleCommentCountFixture()

        // then
        assertThat(articleCommentCount.articleId).isNotNull
        assertThat(articleCommentCount.commentCount).isNotNull
    }


    @Test
    @DisplayName("커스텀 값 지정 테스트")
    fun test2() {
        // given
        val articleId = 134L
        val commentCount = 55L

        // when
        val articleCommentCount = articleCommentCountFixture(
            articleId = articleId,
            commentCount = commentCount
        )

        // then
        assertThat(articleCommentCount.articleId).isEqualTo(articleId)
        assertThat(articleCommentCount.commentCount).isEqualTo(commentCount)
    }
}
