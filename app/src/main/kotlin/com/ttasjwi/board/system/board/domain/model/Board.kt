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
    description: String,
    managerId: Long,
    slug: String,
    val createdAt: AppDateTime,
) {

    var name: String = name
        private set

    var description: String = description
        private set

    var managerId: Long = managerId
        private set

    var slug: String = slug
        private set

    companion object {

        internal fun create(
            id: Long,
            name: String,
            description: String,
            managerId: Long,
            slug: String,
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
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = AppDateTime.from(createdAt),
            )
        }
    }

    override fun toString(): String {
        return "Board(id=$id, name=$name, description=$description, managerId=$managerId, slug=$slug, createdAt=$createdAt)"
    }
}
