package com.ttasjwi.board.system.articleview.domain.model

class ArticleViewCount
internal constructor(
    val articleId: Long,
    val viewCount: Long,
) {


    companion object {

        fun restore(articleId: Long, viewCount: Long): ArticleViewCount {
            return ArticleViewCount(
                articleId = articleId,
                viewCount = viewCount
            )
        }
    }

    override fun toString(): String {
        return "ArticleViewCount(articleId=$articleId, viewCount=$viewCount)"
    }
}
