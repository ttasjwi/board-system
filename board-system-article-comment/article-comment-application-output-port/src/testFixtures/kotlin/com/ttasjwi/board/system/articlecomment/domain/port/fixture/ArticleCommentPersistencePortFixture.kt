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
}
