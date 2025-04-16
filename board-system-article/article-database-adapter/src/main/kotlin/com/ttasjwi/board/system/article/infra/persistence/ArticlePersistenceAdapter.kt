package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaArticle
import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaArticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ArticlePersistenceAdapter(
    private val jpaArticleRepository: JpaArticleRepository
) : ArticlePersistencePort{

    override fun save(article: Article): Article {
        val jpaModel = JpaArticle.from(article)
        jpaArticleRepository.save(jpaModel)
        return article
    }

    override fun findByIdOrNull(articleId: Long): Article? {
        return jpaArticleRepository.findByIdOrNull(articleId)?.retoreToDomain()
    }
}
