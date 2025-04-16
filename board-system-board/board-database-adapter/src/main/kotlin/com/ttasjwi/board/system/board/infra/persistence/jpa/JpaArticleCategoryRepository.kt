package com.ttasjwi.board.system.board.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface JpaArticleCategoryRepository : JpaRepository<JpaArticleCategory, Long> {

    fun existsByBoardIdAndName(boardId: Long, name: String): Boolean
    fun existsByBoardIdAndSlug(boardId: Long, slug: String): Boolean
}
