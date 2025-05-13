package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentWriterNicknamePersistencePort
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaUserRepository
import org.springframework.stereotype.Component

@Component
class ArticleCommentWriterNicknamePersistenceAdapter(
    private val jpaUserRepository: JpaUserRepository
) : ArticleCommentWriterNicknamePersistencePort {

    override fun findCommentWriterNickname(commentWriterId: Long): String? {
        return jpaUserRepository.findNicknameByUserIdOrNull(commentWriterId)
    }
}
