package com.ttasjwi.board.system.articlecomment.domain.port

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentCount

interface ArticleCommentCountPersistencePort {

    fun increase(articleId: Long)
    fun decrease(articleId: Long)
    fun findByIdOrNull(articleId: Long): ArticleCommentCount?
}
