package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.port.ArticleWriterNicknamePersistencePort


class ArticleWriterNicknamePersistencePortFixture : ArticleWriterNicknamePersistencePort {

    private val storage = mutableMapOf<Long, String>()

    fun save(writerId: Long, writerNickname: String): String {
        storage[writerId] = writerNickname
        return writerNickname
    }

    override fun findWriterNicknameOrNull(writerId: Long): String? {
        return storage[writerId]
    }
}
