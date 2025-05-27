package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail
import com.ttasjwi.board.system.articleread.domain.port.ArticleDetailStorage

class ArticleDetailStorageFixture : ArticleDetailStorage {

    private val storage = mutableMapOf<Long, ArticleDetail>()

    fun save(articleDetail: ArticleDetail) {
        storage[articleDetail.articleId] = articleDetail
    }

    override fun read(articleId: Long, userId: Long?): ArticleDetail? {
        return storage[articleId]
    }
}
