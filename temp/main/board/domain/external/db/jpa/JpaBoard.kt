package com.ttasjwi.board.system.board.domain.external.db.jpa

import com.ttasjwi.board.system.board.domain.model.Board
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "boards")
class JpaBoard(

    @Id
    @Column(name = "board_id")
    val id: Long,

    @Column(name = "name", length = 16, unique = true, nullable = false)
    var name: String,

    @Column(name = "description", length = 100, nullable = false)
    var description: String,

    @Column(name = "manager_id", nullable = false)
    var managerId: Long,

    @Column(name = "slug", length = 20, unique = true, nullable = false)
    var slug: String,

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    val createdAt: LocalDateTime,
) {

    companion object {

        internal fun from(board: Board): JpaBoard {
            return JpaBoard(
                id = board.id,
                name = board.name,
                description = board.description,
                managerId = board.managerId,
                slug = board.slug,
                createdAt = board.createdAt.toLocalDateTime(),
            )
        }
    }

    internal fun restoreDomain(): Board {
        return Board.restore(
            id = this.id,
            name = this.name,
            description = this.description,
            managerId = this.managerId,
            slug = this.slug,
            createdAt = this.createdAt
        )
    }
}
