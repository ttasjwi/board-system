package com.ttasjwi.board.system.articlelike.domain.model

class ArticleLikeCount(
    val articleId: Long,
    val likeCount: Long
) {

    companion object {
        fun restore(articleId: Long, likeCount: Long): ArticleLikeCount {
            return ArticleLikeCount(
                articleId = articleId,
                likeCount = likeCount
            )
        }
    }

    override fun toString(): String {
        return "ArticleLikeCount(articleId=$articleId, likeCount=$likeCount)"
    }
}
