package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleLikeCountRepository")
interface JpaArticleLikeCountRepository : JpaRepository<JpaArticleLikeCount, Long>
