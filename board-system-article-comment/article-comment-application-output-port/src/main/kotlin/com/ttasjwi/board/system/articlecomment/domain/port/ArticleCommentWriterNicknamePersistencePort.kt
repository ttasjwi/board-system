package com.ttasjwi.board.system.articlecomment.domain.port

interface ArticleCommentWriterNicknamePersistencePort {

    fun findCommentWriterNickname(commentWriterId: Long): String?
}
