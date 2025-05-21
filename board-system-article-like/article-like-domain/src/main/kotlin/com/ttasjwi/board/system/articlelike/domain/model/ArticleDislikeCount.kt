package com.ttasjwi.board.system.articlelike.domain.model

class ArticleDislikeCount(
    val articleId: Long,
    val dislikeCount: Long
) {

    companion object {

        fun restore(articleId: Long, dislikeCount: Long): ArticleDislikeCount {
            return ArticleDislikeCount(
                articleId = articleId,
                dislikeCount = dislikeCount
            )
        }
    }

    override fun toString(): String {
        return "ArticleDislikeCount(articleId=$articleId, dislikeCount=$dislikeCount)"
    }
}
