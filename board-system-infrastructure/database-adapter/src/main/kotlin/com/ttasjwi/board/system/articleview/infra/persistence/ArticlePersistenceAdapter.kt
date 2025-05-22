package com.ttasjwi.board.system.articleview.infra.persistence

import com.ttasjwi.board.system.articleview.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articleview.infra.persistence.jpa.JpaArticleRepository
import org.springframework.stereotype.Component

@Component("articleViewArticlePersistenceAdapter")
class ArticlePersistenceAdapter(
    private val jpaArticleRepository: JpaArticleRepository
): ArticlePersistencePort {

    override fun existsById(articleId: Long): Boolean {
        return jpaArticleRepository.existsById(articleId)
    }
}
