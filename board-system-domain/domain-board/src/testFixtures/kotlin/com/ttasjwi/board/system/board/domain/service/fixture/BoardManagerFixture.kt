package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.model.fixture.boardFixtureNotRegistered
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

class BoardManagerFixture : BoardManager {

    override fun create(
        name: BoardName,
        description: BoardDescription,
        managerId: MemberId,
        slug: BoardSlug,
        currentTime: ZonedDateTime
    ): Board {
        return boardFixtureNotRegistered(
            name = name.value,
            description = description.value,
            managerId = managerId.value,
            slug = slug.value,
            createdAt = currentTime
        )
    }
}
