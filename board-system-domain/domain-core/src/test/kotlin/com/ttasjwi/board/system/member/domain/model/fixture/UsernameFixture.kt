package com.ttasjwi.board.system.member.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.Username

fun usernameFixture(value: String = "username"): Username {
    return Username(value)
}
