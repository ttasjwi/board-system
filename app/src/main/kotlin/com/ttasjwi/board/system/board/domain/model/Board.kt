package com.ttasjwi.board.system.board.domain.model

import java.time.ZonedDateTime

/**
 * 게시판
 */
class Board
internal constructor(
    val id: Long,
    name: BoardName,
    description: BoardDescription,
    managerId: Long,
    slug: BoardSlug,
    createdAt: ZonedDateTime,
) {

    var name: BoardName = name
        private set

    var description: BoardDescription = description
        private set

    var managerId: Long = managerId
        private set

    var slug: BoardSlug = slug
        private set

    var createdAt: ZonedDateTime = createdAt
        private set

    companion object {

        internal fun create(
            id: Long,
            name: BoardName,
            description: BoardDescription,
            managerId: Long,
            slug: BoardSlug,
            currentTime: ZonedDateTime
        ): Board {
            return Board(
                id = id,
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
                id = id,
                name = BoardName.restore(name),
                description = BoardDescription.restore(description),
                managerId = managerId,
                slug = BoardSlug.restore(slug),
                createdAt = createdAt,
            )
        }
    }

    override fun toString(): String {
        return "Board(id=$id, name=$name, description=$description, managerId=$managerId, slug=$slug, createdAt=$createdAt)"
    }
}
