package com.ttasjwi.board.system.articlelike.domain.model

class ArticleLikeCount(
    val articleId: Long,
    likeCount: Long
) {
    var likeCount: Long = likeCount
        private set

    companion object {
        fun init(articleId: Long): ArticleLikeCount {
            return ArticleLikeCount(
                articleId = articleId,
                likeCount = 0L
            )
        }

        fun restore(articleId: Long, likeCount: Long): ArticleLikeCount {
            return ArticleLikeCount(
                articleId = articleId,
                likeCount = likeCount
            )
        }
    }

    fun increase() {
        this.likeCount += 1
    }

    fun decrease() {
        this.likeCount -= 1
    }

    override fun toString(): String {
        return "ArticleLikeCount(articleId=$articleId, likeCount=$likeCount)"
    }
}
