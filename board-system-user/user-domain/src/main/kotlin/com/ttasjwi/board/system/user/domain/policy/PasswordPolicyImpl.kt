package com.ttasjwi.board.system.user.domain.policy

import com.ttasjwi.board.system.common.annotation.component.DomainPolicy
import com.ttasjwi.board.system.user.domain.exception.InvalidPasswordFormatException
import java.util.*

@DomainPolicy
internal class PasswordPolicyImpl : PasswordPolicy {

    companion object {
        internal const val MIN_LENGTH = 4
        internal const val MAX_LENGTH = 32
        internal const val RANDOM_PASSWORD_LENGTH = 16
    }

    override fun validate(rawPassword: String): Result<String> = kotlin.runCatching {
        if (rawPassword.length < MIN_LENGTH || rawPassword.length > MAX_LENGTH) {
            throw InvalidPasswordFormatException()
        }
        rawPassword
    }

    override fun createRandomPassword(): String {
        return UUID
            .randomUUID()
            .toString()
            .replace(oldValue = "-", newValue = "")
            .substring(startIndex = 0, endIndex = RANDOM_PASSWORD_LENGTH)
    }
}
