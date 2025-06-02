package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleUpdateNotAllowedExceptionByCategoryRuleException 테스트")
class ArticleUpdateNotAllowedByCategoryRuleExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12313541L
        val articleCategoryId = 1213444L
        val exception = ArticleUpdateNotAllowedByCategoryRuleException(
            articleId = articleId,
            articleCategoryId = articleCategoryId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.ArticleUpdateNotAllowed.ByCategoryRule")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, articleCategoryId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글의 카테고리 규칙에 따라, 게시글을 수정할 수 없습니다. (articleId=$articleId, articleCategoryId=$articleCategoryId)")
    }
}
