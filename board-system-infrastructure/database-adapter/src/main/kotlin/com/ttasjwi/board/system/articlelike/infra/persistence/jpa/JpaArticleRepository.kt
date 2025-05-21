package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleRepository")
interface JpaArticleRepository : JpaRepository<JpaArticle, Long>
