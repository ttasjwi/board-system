package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleCategory
import com.ttasjwi.board.system.articlelike.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticleCategoryPersistenceAdapter")
class ArticleCategoryPersistenceAdapter(
    private val jpaArticleCategoryRepository: JpaArticleCategoryRepository
) : ArticleCategoryPersistencePort {

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return jpaArticleCategoryRepository.findByIdOrNull(articleCategoryId)?.restoreDomain()
    }
}
