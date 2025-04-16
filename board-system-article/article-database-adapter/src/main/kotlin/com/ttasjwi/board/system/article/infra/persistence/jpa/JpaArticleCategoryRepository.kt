package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface JpaArticleCategoryRepository : JpaRepository<JpaArticleCategory, Long>
