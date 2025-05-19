package com.ttasjwi.board.system.articlelike.infra.persistence

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleDislikeCount
import com.ttasjwi.board.system.articlelike.infra.persistence.jpa.JpaArticleDislikeCountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleLikeArticleDislikeCountPersistenceAdapter")
class ArticleDislikeCountPersistenceAdapter(
    private val jpaArticleDislikeCountRepository: JpaArticleDislikeCountRepository
) : ArticleDislikeCountPersistencePort {

    override fun save(articleDislikeCount: ArticleDislikeCount): ArticleDislikeCount {
        val jpaEntity = JpaArticleDislikeCount.from(articleDislikeCount)
        jpaArticleDislikeCountRepository.save(jpaEntity)
        return articleDislikeCount
    }

    override fun findByIdOrNull(articleId: Long): ArticleDislikeCount? {
        return jpaArticleDislikeCountRepository.findByIdOrNull(articleId)?.restoreDomain()
    }
}
