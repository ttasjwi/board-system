package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.Nickname

fun nicknameFixture(value: String = "테스트닉네임"): Nickname {
    return Nickname(value)
}
