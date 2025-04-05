package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

fun boardFixture(
    id: Long = 1L,
    name: String = "test",
    description: String = "게시판 설명",
    managerId: Long = 1L,
    slug: String = "testslug",
    createdAt: AppDateTime = appDateTimeFixture()
): Board {
    return Board(
        id = id,
        name = name,
        description = description,
        managerId = managerId,
        slug = slug,
        createdAt = createdAt,
    )
}
