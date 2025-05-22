package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentCount
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleCommentCountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleCommentArticleCommentCountPersistenceAdapter")
class ArticleCommentCountPersistenceAdapter(
    private val jpaArticleCommentCountRepository: JpaArticleCommentCountRepository
) : ArticleCommentCountPersistencePort {

    override fun increase(articleId: Long) {
        jpaArticleCommentCountRepository.increase(articleId)
    }

    override fun decrease(articleId: Long) {
        jpaArticleCommentCountRepository.decrease(articleId)
    }

    override fun findByIdOrNull(articleId: Long): ArticleCommentCount? {
        return jpaArticleCommentCountRepository.findByIdOrNull(articleId)?.restoreDomain()
    }
}
