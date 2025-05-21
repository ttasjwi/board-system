package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleLikeCountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticleLikeCountPersistenceAdapter")
class ArticleLikeCountPersistenceAdapter(
    private val jpaArticleLikeCountRepository: JpaArticleLikeCountRepository
) : ArticleLikeCountPersistencePort {

    override fun increase(articleId: Long) {
        jpaArticleLikeCountRepository.increase(articleId)
    }

    override fun decrease(articleId: Long) {
        jpaArticleLikeCountRepository.decrease(articleId)
    }

    override fun findByIdOrNull(articleId: Long): ArticleLikeCount? {
        return jpaArticleLikeCountRepository.findByIdOrNull(articleId)?.restoreDomain()
    }
}
