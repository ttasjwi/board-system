package com.ttasjwi.board.system.common.auth

interface AccessTokenParsePort {

    fun parse(tokenValue: String): AccessToken
}
