package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.domain.port.ArticleWriterNicknamePersistencePort
import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaUserRepository
import org.springframework.stereotype.Component

@Component
class ArticleWriterNicknamePersistenceAdapter(
    private val jpaUserRepository: JpaUserRepository
) : ArticleWriterNicknamePersistencePort {

    override fun findWriterNicknameOrNull(writerId: Long): String? {
        return jpaUserRepository.findNicknameByUserIdOrNull(writerId)
    }
}
