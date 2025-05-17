package com.ttasjwi.board.system.articlelike.domain.model

class Article
internal constructor(
    val articleId: Long,
    val articleCategoryId: Long,
) {

    companion object {

        fun restore(
            articleId: Long,
            articleCategoryId: Long,
        ): Article {
            return Article(
                articleId = articleId,
                articleCategoryId = articleCategoryId,
            )
        }
    }

    override fun toString(): String {
        return "Article(articleId=$articleId, articleCategoryId=$articleCategoryId)"
    }
}
