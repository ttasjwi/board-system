package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleLikeRepository")
interface JpaArticleLikeRepository : JpaRepository<JpaArticleLike, Long> {

    fun findByArticleIdAndUserId(articleId: Long, userId: Long): JpaArticleLike?
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
}
