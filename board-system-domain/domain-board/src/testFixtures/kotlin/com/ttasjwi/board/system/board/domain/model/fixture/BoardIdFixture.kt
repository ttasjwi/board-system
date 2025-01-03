package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.BoardId

fun boardIdFixture(
    value: Long = 1L,
): BoardId {
    return BoardId(value)
}
