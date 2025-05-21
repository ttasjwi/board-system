package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.Article
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticlePersistenceAdapter")
class ArticlePersistenceAdapter(
    private val jpaArticleRepository: JpaArticleRepository
) : ArticlePersistencePort {

    override fun findByIdOrNull(articleId: Long): Article? {
        return jpaArticleRepository.findByIdOrNull(articleId)?.retoreToDomain()
    }

    override fun existsByArticleId(articleId: Long): Boolean {
        return jpaArticleRepository.existsById(articleId)
    }
}
