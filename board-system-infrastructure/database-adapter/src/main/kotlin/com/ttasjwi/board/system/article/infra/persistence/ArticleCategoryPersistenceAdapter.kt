package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.domain.model.ArticleCategory
import com.ttasjwi.board.system.article.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaArticleCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleArticleCategoryPersistenceAdapter")
class ArticleCategoryPersistenceAdapter(
    private val jpaArticleCategoryRepository: JpaArticleCategoryRepository
): ArticleCategoryPersistencePort {

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return jpaArticleCategoryRepository.findByIdOrNull(articleCategoryId)?.restoreDomain()
    }
}
