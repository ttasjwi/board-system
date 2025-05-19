package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleDislikeRepository")
interface JpaArticleDislikeRepository : JpaRepository<JpaArticleDislike, Long> {

    fun findByArticleIdAndUserId(articleId: Long, userId: Long): JpaArticleDislike?
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean
}
