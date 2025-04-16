package com.ttasjwi.board.system.user.domain.policy

interface PasswordPolicy {

    fun validate(rawPassword: String): Result<String>
    fun createRandomPassword(): String
}
