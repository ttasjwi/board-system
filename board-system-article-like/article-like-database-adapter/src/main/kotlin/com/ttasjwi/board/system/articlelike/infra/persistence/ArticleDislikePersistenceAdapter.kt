package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikePersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleDislike
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleDislikeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleDislikeArticleLikePersistenceAdapter")
class ArticleDislikePersistenceAdapter(
    private val jpaArticleDislikeRepository: JpaArticleDislikeRepository
) : ArticleDislikePersistencePort {

    override fun save(articleDislike: ArticleDislike): ArticleDislike {
        val jpaEntity = JpaArticleDislike.from(articleDislike)
        jpaArticleDislikeRepository.save(jpaEntity)
        return articleDislike
    }

    override fun findByIdOrNullTest(articleDislikeId: Long): ArticleDislike? {
        return jpaArticleDislikeRepository.findByIdOrNull(articleDislikeId)?.restoreDomain()
    }

    override fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleDislike? {
        return jpaArticleDislikeRepository.findByArticleIdAndUserId(articleId, userId)?.restoreDomain()
    }

    override fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean {
        return jpaArticleDislikeRepository.existsByArticleIdAndUserId(articleId, userId)
    }
}
