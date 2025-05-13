package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentWriterNicknamePersistencePort

class ArticleCommentWriterNicknamePersistencePortFixture : ArticleCommentWriterNicknamePersistencePort {

    private val storage = mutableMapOf<Long, String>()

    fun save(commentWriterId: Long, commentWriterNickname: String): String {
        storage[commentWriterId] = commentWriterNickname
        return commentWriterNickname
    }

    override fun findCommentWriterNickname(commentWriterId: Long): String? {
        return storage[commentWriterId]
    }
}
