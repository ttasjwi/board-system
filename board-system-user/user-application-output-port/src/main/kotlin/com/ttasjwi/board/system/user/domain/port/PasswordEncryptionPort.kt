package com.ttasjwi.board.system.user.domain.port

interface PasswordEncryptionPort {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
