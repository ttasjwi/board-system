package com.ttasjwi.board.system.articleview.domain.port.fixture

import com.ttasjwi.board.system.articleview.domain.port.ArticlePersistencePort

class ArticlePersistencePortFixture : ArticlePersistencePort {

    private val storage = mutableSetOf<Long>()

    fun save(articleId: Long) {
        storage.add(articleId)
    }

    override fun existsById(articleId: Long): Boolean {
        return storage.contains(articleId)
    }
}
