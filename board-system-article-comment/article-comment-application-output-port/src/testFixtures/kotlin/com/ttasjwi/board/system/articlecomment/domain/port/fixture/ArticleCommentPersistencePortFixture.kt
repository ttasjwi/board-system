package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort

class ArticleCommentPersistencePortFixture : ArticleCommentPersistencePort {

    private val storage = mutableMapOf<Long, ArticleComment>()

    override fun save(articleComment: ArticleComment): ArticleComment {
        storage[articleComment.articleCommentId] = articleComment
        return articleComment
    }

    override fun findById(articleCommentId: Long): ArticleComment? {
        return storage[articleCommentId]
    }

    override fun findAllPage(articleId: Long, offset: Long, limit: Long): List<ArticleComment> {
        return storage.values
            .filter { it.articleId == articleId }
            .sortedWith(
                compareBy<ArticleComment> { it.rootParentCommentId }
                    .thenBy { it.articleCommentId }
            )
            .drop(offset.toInt())
            .take(limit.toInt())
    }

    override fun findAllInfiniteScroll(
        articleId: Long,
        limit: Long,
        lastRootParentCommentId: Long?,
        lastCommentId: Long?
    ): List<ArticleComment> {
        return storage.values
            .asSequence()
            .filter { it.articleId == articleId }
            .sortedWith(compareBy<ArticleComment> { it.rootParentCommentId }.thenBy { it.articleCommentId })
            .dropWhile { comment ->
                // dropWhile : true를 반환하면 포함 x, 처음으로 false를 반환하는 것부터 포함.
                if (lastRootParentCommentId != null && lastCommentId != null) {
                    comment.rootParentCommentId < lastRootParentCommentId ||
                            (comment.rootParentCommentId == lastRootParentCommentId && comment.articleCommentId <= lastCommentId)
                } else {
                    false
                }
            }
            .take(limit.toInt())
            .toList()
    }
}
