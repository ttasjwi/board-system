package com.ttasjwi.board.system.articlecomment.infra.persistence

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleComment
import com.ttasjwi.board.system.articlecomment.infra.persistence.jpa.JpaArticleCommentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ArticleCommentPersistenceAdapter(
    private val jpaArticleCommentRepository: JpaArticleCommentRepository
) : ArticleCommentPersistencePort {

    override fun save(articleComment: ArticleComment): ArticleComment {
        val jpaEntity = JpaArticleComment.from(articleComment)
        jpaArticleCommentRepository.save(jpaEntity)
        return articleComment
    }

    override fun findById(articleCommentId: Long): ArticleComment? {
        return jpaArticleCommentRepository.findByIdOrNull(articleCommentId)?.restoreDomain()
    }
}
