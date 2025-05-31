package com.ttasjwi.board.system.article.domain.model

class ArticleCategory(
    val articleCategoryId: Long,
    val boardId: Long,
    val allowSelfEditDelete: Boolean,
) {

    companion object {

        fun restore(
            articleCategoryId: Long,
            boardId: Long,
            allowSelfEditDelete: Boolean
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                boardId = boardId,
                allowSelfEditDelete = allowSelfEditDelete
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, boardId=$boardId, allowSelfEditDelete=$allowSelfEditDelete)"
    }
}
