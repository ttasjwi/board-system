package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import java.time.ZonedDateTime

fun boardFixtureNotRegistered(
    name: String = "test",
    description: String = "게시판 설명",
    managerId: Long = 1L,
    slug: String = "testslug",
    createdAt: ZonedDateTime = timeFixture()
): Board {
    return Board(
        name = boardNameFixture(name),
        description = boardDescriptionFixture(description),
        managerId = memberIdFixture(managerId),
        slug = boardSlugFixture(slug),
        createdAt = createdAt,
    )
}

fun boardFixtureRegistered(
    id: Long = 1L,
    name: String = "test",
    description: String = "게시판 설명",
    managerId: Long = 1L,
    slug: String = "testslug",
    createdAt: ZonedDateTime = timeFixture()
): Board {
    return Board(
        id = boardIdFixture(id),
        name = boardNameFixture(name),
        description = boardDescriptionFixture(description),
        managerId = memberIdFixture(managerId),
        slug = boardSlugFixture(slug),
        createdAt = createdAt,
    )
}
