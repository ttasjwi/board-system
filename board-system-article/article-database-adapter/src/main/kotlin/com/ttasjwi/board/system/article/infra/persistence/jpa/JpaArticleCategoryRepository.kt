package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("articleJpaArticleCategoryRepository")
interface JpaArticleCategoryRepository : JpaRepository<JpaArticleCategory, Long>
