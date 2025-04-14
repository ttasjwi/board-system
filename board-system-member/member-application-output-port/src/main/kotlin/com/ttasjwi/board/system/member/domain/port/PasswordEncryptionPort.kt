package com.ttasjwi.board.system.member.domain.port

interface PasswordEncryptionPort {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
