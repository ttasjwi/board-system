package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount

interface ArticleLikeCountPersistencePort {

    fun increase(articleId: Long)
    fun decrease(articleId: Long)
    fun findByIdOrNull(articleId: Long): ArticleLikeCount?
}
