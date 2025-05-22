package com.ttasjwi.board.system.articleview.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleViewJpaArticleRepository")
interface JpaArticleRepository : JpaRepository<JpaArticle, Long>
