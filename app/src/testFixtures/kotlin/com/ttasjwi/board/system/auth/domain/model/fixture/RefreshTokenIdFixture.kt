package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId

fun refreshTokenIdFixture(
    value: String = "refreshTokenId"
): RefreshTokenId {
    return RefreshTokenId.testCreate(value)
}

private fun RefreshTokenId.Companion.testCreate(
    value: String
): RefreshTokenId {
    return RefreshTokenId(value)
}
