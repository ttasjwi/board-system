package com.ttasjwi.board.system.article.domain.model

class ArticleCategory(
    val articleCategoryId: Long,
    val boardId: Long,
    val allowWrite: Boolean,
    val allowSelfEditDelete: Boolean,
) {

    companion object {

        fun restore(
            articleCategoryId: Long,
            boardId: Long,
            allowWrite: Boolean,
            allowSelfEditDelete: Boolean
        ): ArticleCategory {
            return ArticleCategory(
                articleCategoryId = articleCategoryId,
                boardId = boardId,
                allowWrite = allowWrite,
                allowSelfEditDelete = allowSelfEditDelete
            )
        }
    }

    override fun toString(): String {
        return "ArticleCategory(articleCategoryId=$articleCategoryId, boardId=$boardId, allowWrite=$allowWrite, allowSelfEditDelete=$allowSelfEditDelete)"
    }
}
