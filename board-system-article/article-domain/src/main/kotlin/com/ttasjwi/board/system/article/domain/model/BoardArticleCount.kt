package com.ttasjwi.board.system.article.domain.model

class BoardArticleCount(
    val boardId: Long,
    val articleCount: Long
) {

    companion object {

        fun restore(boardId: Long, articleCount: Long): BoardArticleCount {
            return BoardArticleCount(boardId, articleCount)
        }
    }

    override fun toString(): String {
        return "BoardArticleCount(boardId=$boardId, articleCount=$articleCount)"
    }
}
