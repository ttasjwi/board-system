package com.ttasjwi.board.system.article.domain.port

import com.ttasjwi.board.system.article.domain.model.BoardArticleCount

interface BoardArticleCountPersistencePort {

    fun increase(boardId: Long)
    fun decrease(boardId: Long)
    fun findByIdOrNull(boardId: Long): BoardArticleCount?
}
