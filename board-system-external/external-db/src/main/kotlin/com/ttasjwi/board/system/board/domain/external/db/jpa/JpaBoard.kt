package com.ttasjwi.board.system.board.domain.external.db.jpa

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.core.time.TimeRule
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name= "boards")
class JpaBoard (

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", unique = true, nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "manger_id", nullable = false)
    var managerId: Long,

    @Column(name = "slug", unique = true, nullable = false)
    var slug: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime,
) {

    companion object {

        internal fun from(board: Board): JpaBoard {
            return JpaBoard(
                id = board.id?.value,
                name = board.name.value,
                description = board.description.value,
                managerId = board.managerId.value,
                slug = board.slug.value,
                createdAt = board.createdAt,
            )
        }
    }

    internal fun restoreDomain(): Board {
        return Board.restore(
            id = this.id!!,
            name = this.name,
            description = this.description,
            managerId = this.managerId,
            slug = this.slug,
            createdAt = this.createdAt.withZoneSameInstant(TimeRule.ZONE_ID)
        )
    }
}
