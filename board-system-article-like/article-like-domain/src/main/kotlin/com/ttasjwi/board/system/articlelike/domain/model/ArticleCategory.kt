package com.ttasjwi.board.system.articlelike.domain.model

class ArticleCategory
internal constructor(
    val articleCategoryId: Long,
    val allowLike: Boolean,
    val allowDislike: Boolean,
) {

    companion object {
        fun restore(
            articleCategoryId: Long,
            allowLike: Boolean,
            allowDislike: Boolean
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                allowLike = allowLike,
                allowDislike = allowDislike
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, allowLike=$allowLike, allowDislike=$allowDislike)"
    }
}
