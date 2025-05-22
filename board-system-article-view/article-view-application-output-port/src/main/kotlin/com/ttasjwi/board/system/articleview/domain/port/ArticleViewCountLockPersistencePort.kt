package com.ttasjwi.board.system.articleview.domain.port

import java.time.Duration

interface ArticleViewCountLockPersistencePort {
    fun lock(articleId: Long, userId: Long, ttl: Duration): Boolean
}
