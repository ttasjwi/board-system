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

    override fun findAllPage(articleId: Long, offset: Long, limit: Long): List<ArticleComment> {
        return jpaArticleCommentRepository
            .findAllPage(articleId, offset, limit)
            .map { it.restoreDomain() }
    }

    override fun count(articleId: Long, limit: Long): Long {
        return jpaArticleCommentRepository.count(articleId, limit)
    }

    override fun findAllInfiniteScroll(
        articleId: Long,
        limit: Long,
        lastRootParentCommentId: Long?,
        lastCommentId: Long?
    ): List<ArticleComment> {
        val jpaComments = if (lastRootParentCommentId != null && lastCommentId != null) {
            jpaArticleCommentRepository.findAllInfiniteScroll(articleId, limit, lastRootParentCommentId, lastCommentId)
        } else {
            jpaArticleCommentRepository.findAllInfiniteScroll(articleId, limit)
        }
        return jpaComments.map { it.restoreDomain() }
    }
}
