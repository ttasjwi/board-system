package com.ttasjwi.board.system.articlecomment.domain.model

class ArticleCategory
internal constructor(
    val articleCategoryId: Long,
    val allowComment: Boolean,
) {

    companion object {
        fun restore(
            articleCategoryId: Long,
            allowComment: Boolean,
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                allowComment = allowComment,
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, allowComment=$allowComment)"
    }
}
