package com.ttasjwi.board.system.articlecomment.domain.model

class ArticleCommentCount(
    val articleId: Long,
    val commentCount: Long
) {

    companion object {

        fun restore(articleId: Long, commentCount: Long): ArticleCommentCount {
            return ArticleCommentCount(articleId, commentCount)
        }
    }

    override fun toString(): String {
        return "ArticleCommentCount(articleId=$articleId, commentCount=$commentCount)"
    }
}
