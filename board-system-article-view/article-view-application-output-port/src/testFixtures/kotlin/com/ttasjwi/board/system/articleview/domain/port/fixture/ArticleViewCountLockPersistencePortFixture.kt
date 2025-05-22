package com.ttasjwi.board.system.articleview.domain.port.fixture

import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountLockPersistencePort
import java.time.Duration

class ArticleViewCountLockPersistencePortFixture : ArticleViewCountLockPersistencePort {

    companion object {
        const val LOCK_USER_ID = 134513513251531L
    }

    override fun lock(articleId: Long, userId: Long, ttl: Duration): Boolean {
        return userId != LOCK_USER_ID
    }
}
