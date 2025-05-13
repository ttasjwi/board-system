package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.domain.model.Article
import com.ttasjwi.board.system.articlecomment.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ArticlePersistenceAdapter(
    private val jpaArticleRepository: JpaArticleRepository
) : ArticlePersistencePort {

    override fun findById(articleId: Long): Article? {
        return jpaArticleRepository.findByIdOrNull(articleId)?.restoreDomain()
    }
}
