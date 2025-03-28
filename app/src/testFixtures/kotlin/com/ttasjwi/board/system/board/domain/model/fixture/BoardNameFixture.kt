package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.BoardName

fun boardNameFixture(
    value: String = "테스트",
): BoardName {
    return BoardName(value)
}
