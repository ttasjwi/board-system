package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleReadJpaBoardArticleCountRepository")
interface JpaBoardArticleCountRepository : JpaRepository<JpaBoardArticleCount, Long> {

    @Query(
        """
       SELECT ac.articleCount
       FROM ArticleReadJpaBoardArticleCount ac
       WHERE ac.boardId = :boardId
    """)
    fun count(@Param("boardId") boardId: Long): Long?
}
