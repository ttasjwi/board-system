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

    override fun count(articleId: Long, limit: Long): Long {
        return storage.values
            .filter { it.articleId == articleId }
            .take(limit.toInt())
            .count()
            .toLong()
    }
}
