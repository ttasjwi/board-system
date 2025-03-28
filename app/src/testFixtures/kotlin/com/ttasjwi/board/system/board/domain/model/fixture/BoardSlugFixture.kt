package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.BoardSlug

fun boardSlugFixture(
    value: String = "testslug",
): BoardSlug {
    return BoardSlug(value)
}
