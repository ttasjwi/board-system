package com.ttasjwi.board.system.articleread.domain.port

interface BoardArticleCountStorage {
    fun count(boardId: Long): Long
}
