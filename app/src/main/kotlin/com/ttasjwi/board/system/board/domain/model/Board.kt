package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

/**
 * 게시판
 */
class Board
internal constructor(
    val id: Long,
    name: String,
    description: BoardDescription,
    managerId: Long,
    slug: BoardSlug,
    val createdAt: AppDateTime,
) {

    var name: String = name
        private set

    var description: BoardDescription = description
        private set

    var managerId: Long = managerId
        private set

    var slug: BoardSlug = slug
        private set

    companion object {

        internal fun create(
            id: Long,
            name: String,
            description: BoardDescription,
            managerId: Long,
            slug: BoardSlug,
            currentTime: AppDateTime
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
            createdAt: LocalDateTime,
        ): Board {
            return Board(
                id = id,
                name = name,
                description = BoardDescription.restore(description),
                managerId = managerId,
                slug = BoardSlug.restore(slug),
                createdAt = AppDateTime.from(createdAt),
            )
        }
    }

    override fun toString(): String {
        return "Board(id=$id, name=$name, description=$description, managerId=$managerId, slug=$slug, createdAt=$createdAt)"
    }
}
