package com.ttasjwi.board.system.articleview.domain.port

import com.ttasjwi.board.system.articleview.domain.model.ArticleViewCount

interface ArticleViewCountPersistencePort {

    fun increase(articleId: Long)
    fun findByIdOrNull(articleId: Long): ArticleViewCount?
}
