package com.ttasjwi.board.system.domain.member.external

interface ExternalPasswordHandler {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
