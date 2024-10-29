package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.MemberId

fun memberIdFixture(
    value: Long = 1L,
): MemberId {
    return MemberId(value)
}
