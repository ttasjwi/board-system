package com.ttasjwi.board.system.articlecomment.domain.model

class Article(
    val articleId: Long,
    val writerId: Long,
    val articleCategoryId: Long,
) {

    companion object {

        fun restore(
            articleId: Long,
            writerId: Long,
            articleCategoryId: Long,
        ): Article {
            return Article(
                articleId = articleId,
                writerId = writerId,
                articleCategoryId = articleCategoryId,
            )
        }
    }

    override fun toString(): String {
        return "Article(articleId=$articleId, writerId=$writerId, articleCategoryId=$articleCategoryId)"
    }
}
