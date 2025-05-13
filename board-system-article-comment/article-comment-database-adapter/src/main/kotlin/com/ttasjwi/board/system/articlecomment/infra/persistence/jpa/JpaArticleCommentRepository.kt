package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleCommentJpaArticleCommentRepository")
interface JpaArticleCommentRepository : JpaRepository<JpaArticleComment, Long>
