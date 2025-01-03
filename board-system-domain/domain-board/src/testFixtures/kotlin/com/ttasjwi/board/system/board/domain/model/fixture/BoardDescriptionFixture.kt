package com.ttasjwi.board.system.board.domain.model.fixture

import com.ttasjwi.board.system.board.domain.model.BoardDescription

fun boardDescriptionFixture(
    value: String = "게시판 설명",
): BoardDescription {
    return BoardDescription(value)
}
