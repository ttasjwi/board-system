package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.EncodedPassword

fun encodedPasswordFixture(
    value: String = "1111"
): EncodedPassword {
    return EncodedPassword(value)
}
