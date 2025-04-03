package com.ttasjwi.board.system.member.domain.service

interface PasswordManager {
    fun validateRawPassword(rawPassword: String): Result<String>
    fun createRandomRawPassword(): String
    fun encode(rawPassword: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}
