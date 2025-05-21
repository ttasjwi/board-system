package com.ttasjwi.board.system.board.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaBoardRepository : JpaRepository<JpaBoard, Long> {


    @Query("SELECT b FROM JpaBoard b WHERE b.slug = :slug")
    fun findBySlugOrNull(@Param("slug") slug: String): JpaBoard?
    fun existsByName(name: String): Boolean
    fun existsBySlug(slug: String): Boolean
}
