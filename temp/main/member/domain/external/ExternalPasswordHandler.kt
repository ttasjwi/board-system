package com.ttasjwi.board.system.member.domain.external

interface ExternalPasswordHandler {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
