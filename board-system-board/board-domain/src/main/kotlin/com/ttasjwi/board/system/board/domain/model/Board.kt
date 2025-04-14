package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

/**
 * 게시판
 */
class Board
internal constructor(
    val boardId: Long,
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

        fun create(
            boardId: Long,
            name: String,
            description: String,
            managerId: Long,
            slug: String,
            currentTime: AppDateTime
        ): Board {
            return Board(
                boardId = boardId,
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = currentTime,
            )
        }

        fun restore(
            boardId: Long,
            name: String,
            description: String,
            managerId: Long,
            slug: String,
            createdAt: LocalDateTime,
        ): Board {
            return Board(
                boardId = boardId,
                name = name,
                description = description,
                managerId = managerId,
                slug = slug,
                createdAt = AppDateTime.from(createdAt),
            )
        }
    }

    override fun toString(): String {
        return "Board(boardId=$boardId, name=$name, description=$description, managerId=$managerId, slug=$slug, createdAt=$createdAt)"
    }
}
