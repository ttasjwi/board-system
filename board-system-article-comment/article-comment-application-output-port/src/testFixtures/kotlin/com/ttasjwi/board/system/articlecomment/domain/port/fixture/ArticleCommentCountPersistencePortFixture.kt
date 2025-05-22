package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentCount
import com.ttasjwi.board.system.articlecomment.domain.model.fixture.articleCommentCountFixture
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort

class ArticleCommentCountPersistencePortFixture : ArticleCommentCountPersistencePort {

    private val storage = mutableMapOf<Long, ArticleCommentCount>()

    override fun increase(articleId: Long) {
        if (!storage.containsKey(articleId)) {
            storage[articleId] = articleCommentCountFixture(articleId = articleId, commentCount = 1)
        } else{
            val articleCommentCount = storage[articleId]!!
            storage[articleId] = articleCommentCountFixture(articleId = articleId, commentCount = articleCommentCount.commentCount + 1)
        }
    }

    override fun decrease(articleId: Long) {
        // 전제 : articleId 의 articleCommentCount 가 있어야함 (애플리케이션 로직에서 보장)
        val articleCommentCount = storage[articleId]!!
        storage[articleId] = articleCommentCountFixture(articleId = articleId, commentCount = articleCommentCount.commentCount - 1)
    }

    override fun findByIdOrNull(articleId: Long): ArticleCommentCount? {
        return storage[articleId]
    }
}
