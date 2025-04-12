package com.ttasjwi.board.system.common.auth

interface RefreshTokenParsePort {
    fun parse(tokenValue: String): RefreshToken
}
