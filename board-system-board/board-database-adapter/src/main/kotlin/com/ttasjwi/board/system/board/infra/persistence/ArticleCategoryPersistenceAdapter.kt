package com.ttasjwi.board.system.board.infra.persistence

import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.board.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaArticleCategory
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaArticleCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ArticleCategoryPersistenceAdapter(
    private val jpaArticleCategoryRepository: JpaArticleCategoryRepository
) : ArticleCategoryPersistencePort {

    override fun save(articleCategory: ArticleCategory): ArticleCategory {
        val jpaModel = JpaArticleCategory.from(articleCategory)
        jpaArticleCategoryRepository.save(jpaModel)
        return articleCategory
    }

    override fun findByIdOrNull(articleCategoryId: Long): ArticleCategory? {
        return jpaArticleCategoryRepository
            .findByIdOrNull(articleCategoryId)
            ?.restoreDomain()
    }

    override fun existsByName(boardId: Long, articleCategoryName: String): Boolean {
        return jpaArticleCategoryRepository.existsByBoardIdAndName(boardId, articleCategoryName)
    }

    override fun existsBySlug(boardId: Long, articleCategorySlug: String): Boolean {
        return jpaArticleCategoryRepository.existsByBoardIdAndSlug(boardId, articleCategorySlug)
    }
}
