package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleDislikeCountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticleDislikeCountPersistenceAdapter")
class ArticleDislikeCountPersistenceAdapter(
    private val jpaArticleDislikeCountRepository: JpaArticleDislikeCountRepository
) : ArticleDislikeCountPersistencePort {

    override fun increase(articleId: Long) {
        jpaArticleDislikeCountRepository.increase(articleId)
    }

    override fun decrease(articleId: Long) {
        jpaArticleDislikeCountRepository.decrease(articleId)
    }

    override fun findByIdOrNull(articleId: Long): ArticleDislikeCount? {
        return jpaArticleDislikeCountRepository.findByIdOrNull(articleId)?.restoreDomain()
    }
}
