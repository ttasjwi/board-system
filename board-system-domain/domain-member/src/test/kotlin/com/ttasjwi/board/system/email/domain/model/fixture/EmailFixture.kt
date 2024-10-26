package com.ttasjwi.board.system.email.domain.model.fixture

import com.ttasjwi.board.system.email.domain.model.Email

fun emailFixture(
    value: String = "test@gmail.com"
): Email {
    return Email(value)
}
