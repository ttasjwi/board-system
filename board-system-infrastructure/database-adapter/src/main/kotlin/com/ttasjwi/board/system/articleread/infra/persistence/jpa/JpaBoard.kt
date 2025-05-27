package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleReadJpaBoard")
@Table(name = "boards")
class JpaBoard(

    @Id
    @Column(name = "board_id")
    val boardId: Long,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "manager_id")
    val managerId: Long,

    @Column(name = "slug")
    val slug: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
)
