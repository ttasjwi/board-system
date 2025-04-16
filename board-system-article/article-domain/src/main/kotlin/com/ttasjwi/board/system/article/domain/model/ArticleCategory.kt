package com.ttasjwi.board.system.article.domain.model

class ArticleCategory(
    val articleCategoryId: Long,
    val boardId: Long,
    val allowSelfDelete: Boolean,
) {

    companion object {

        fun restore(
            articleCategoryId: Long,
            boardId: Long,
            allowSelfDelete: Boolean
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                boardId = boardId,
                allowSelfDelete = allowSelfDelete
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, boardId=$boardId, allowSelfDelete=$allowSelfDelete)"
    }
}
