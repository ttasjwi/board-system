package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikePersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleLike
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleLikeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticleLikePersistenceAdapter")
class ArticleLikePersistenceAdapter(
    private val jpaArticleLikeRepository: JpaArticleLikeRepository
) : ArticleLikePersistencePort {

    override fun save(articleLike: ArticleLike): ArticleLike {
        val jpaEntity = JpaArticleLike.from(articleLike)
        jpaArticleLikeRepository.save(jpaEntity)
        return articleLike
    }

    override fun findByIdOrNullTest(articleLikeId: Long): ArticleLike? {
        return jpaArticleLikeRepository.findByIdOrNull(articleLikeId)?.restoreDomain()
    }

    override fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleLike? {
        return jpaArticleLikeRepository.findByArticleIdAndUserId(articleId, userId)?.restoreDomain()
    }

    override fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean {
        return jpaArticleLikeRepository.existsByArticleIdAndUserId(articleId, userId)
    }

    override fun remove(articleId: Long, userId: Long) {
        return jpaArticleLikeRepository.deleteByArticleIdAndUserId(articleId, userId)
    }
}
