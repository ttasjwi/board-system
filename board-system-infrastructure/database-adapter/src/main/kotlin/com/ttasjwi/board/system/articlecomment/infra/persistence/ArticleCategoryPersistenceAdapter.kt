package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCategory
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleCommentArticleCategoryPersistenceAdapter")
class ArticleCategoryPersistenceAdapter(
    private val jpaArticleCategoryRepository: JpaArticleCategoryRepository
) : ArticleCategoryPersistencePort {

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return jpaArticleCategoryRepository.findByIdOrNull(articleCategoryId)?.restoreDomain()
    }
}
