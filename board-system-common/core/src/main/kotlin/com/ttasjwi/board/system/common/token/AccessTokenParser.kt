package com.ttasjwi.board.system.common.token

interface AccessTokenParser {

    fun parse(tokenValue: String): AccessToken
}
