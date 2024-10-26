package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.RawPassword

fun rawPasswordFixture(
    value: String = "1111"
): RawPassword {
    return RawPassword(value)
}
