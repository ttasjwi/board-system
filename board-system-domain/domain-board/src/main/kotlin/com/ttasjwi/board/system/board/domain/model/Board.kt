package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.core.domain.model.DomainEntity
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

/**
 * 게시판
 */
class Board
internal constructor(
    id: BoardId? = null,
    name: BoardName,
    description: BoardDescription,
    managerId: MemberId,
    slug: BoardSlug,
    createdAt: ZonedDateTime,
) : DomainEntity<BoardId>(id) {

    var name: BoardName = name
        private set

    var description: BoardDescription = description
        private set

    var managerId: MemberId = managerId
        private set

    var slug: BoardSlug = slug
        private set

    var createdAt: ZonedDateTime = createdAt
        private set

    companion object {

        internal fun create(
            name: BoardName,
            description: BoardDescription,
            managerId: MemberId,
            slug: BoardSlug,
            currentTime: ZonedDateTime
        ): Board {
            return Board(
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = currentTime,
            )
        }

        fun restore(
            id: Long,
            name: String,
            description: String,
            managerId: Long,
            slug: String,
            createdAt: ZonedDateTime,
        ): Board {
            return Board(
                id = BoardId.restore(id),
                name = BoardName.restore(name),
                description = BoardDescription.restore(description),
                managerId = MemberId.restore(managerId),
                slug = BoardSlug.restore(slug),
                createdAt = createdAt,
            )
        }
    }

    override fun toString(): String {
        return "Board(id=$id, name=$name, description=$description, managerId=$managerId, slug=$slug, createdAt=$createdAt)"
    }
}
