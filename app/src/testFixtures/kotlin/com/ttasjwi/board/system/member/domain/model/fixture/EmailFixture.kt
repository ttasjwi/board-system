package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.Email

fun emailFixture(
    value: String = "test@gmail.com"
): Email {
    return Email(value)
}
