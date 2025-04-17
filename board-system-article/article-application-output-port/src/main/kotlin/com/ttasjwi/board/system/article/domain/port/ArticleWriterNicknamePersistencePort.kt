package com.ttasjwi.board.system.article.domain.port

interface ArticleWriterNicknamePersistencePort {
    fun findWriterNicknameOrNull(writerId: Long): String?
}
