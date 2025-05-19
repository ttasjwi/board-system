package com.ttasjwi.board.system.articlelike.domain.model

class ArticleDislikeCount(
    val articleId: Long,
    dislikeCount: Long
) {
    var dislikeCount: Long = dislikeCount
        private set

    companion object {
        fun init(articleId: Long): ArticleDislikeCount {
            return ArticleDislikeCount(
                articleId = articleId,
                dislikeCount = 0L
            )
        }

        fun restore(articleId: Long, dislikeCount: Long): ArticleDislikeCount {
            return ArticleDislikeCount(
                articleId = articleId,
                dislikeCount = dislikeCount
            )
        }
    }

    fun increase() {
        this.dislikeCount += 1
    }

    fun decrease() {
        this.dislikeCount -= 1
    }

    override fun toString(): String {
        return "ArticleDislikeCount(articleId=$articleId, dislikeCount=$dislikeCount)"
    }
}
