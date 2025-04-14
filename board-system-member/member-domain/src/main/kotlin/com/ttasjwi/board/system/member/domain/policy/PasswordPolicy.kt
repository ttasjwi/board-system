package com.ttasjwi.board.system.member.domain.policy

interface PasswordPolicy {

    fun validate(rawPassword: String): Result<String>
    fun createRandomPassword(): String
}
