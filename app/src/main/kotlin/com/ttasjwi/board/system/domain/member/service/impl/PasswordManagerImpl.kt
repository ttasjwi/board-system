package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.domain.member.exception.InvalidPasswordFormatException
import com.ttasjwi.board.system.domain.member.external.ExternalPasswordHandler
import com.ttasjwi.board.system.domain.member.service.PasswordManager
import java.util.*

@DomainService
internal class PasswordManagerImpl(
    private val externalPasswordHandler: ExternalPasswordHandler
) : PasswordManager {

    companion object {
        internal const val MIN_LENGTH = 4
        internal const val MAX_LENGTH = 32
        internal const val RANDOM_PASSWORD_LENGTH = 16
    }

    override fun validateRawPassword(rawPassword: String): Result<String> = kotlin.runCatching {
        if (rawPassword.length < MIN_LENGTH || rawPassword.length > MAX_LENGTH) {
            throw InvalidPasswordFormatException()
        }
        rawPassword
    }

    override fun createRandomRawPassword(): String {
        return UUID.randomUUID().toString().replace("-", "")
            .substring(0, RANDOM_PASSWORD_LENGTH)
    }

    override fun encode(rawPassword: String): String {
        return externalPasswordHandler.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return externalPasswordHandler.matches(rawPassword, encodedPassword)
    }
}
