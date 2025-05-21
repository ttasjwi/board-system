package com.ttasjwi.board.system.articlelike.domain.port

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount

interface ArticleDislikeCountPersistencePort {

    fun increase(articleId: Long)
    fun decrease(articleId: Long)
    fun findByIdOrNull(articleId: Long): ArticleDislikeCount?
}
