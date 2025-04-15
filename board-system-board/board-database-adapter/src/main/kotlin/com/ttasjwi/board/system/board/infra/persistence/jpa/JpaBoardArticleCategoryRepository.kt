package com.ttasjwi.board.system.board.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface JpaBoardArticleCategoryRepository : JpaRepository<JpaBoardArticleCategory, Long> {

    fun existsByBoardIdAndName(boardId: Long, name: String): Boolean
    fun existsByBoardIdAndSlug(boardId: Long, slug: String): Boolean
}
