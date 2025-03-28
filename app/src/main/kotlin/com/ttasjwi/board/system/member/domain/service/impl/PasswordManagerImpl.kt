package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler
import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.service.PasswordManager

@DomainService
internal class PasswordManagerImpl(
    private val externalPasswordHandler: ExternalPasswordHandler
) : PasswordManager {

    override fun createRawPassword(value: String): Result<RawPassword> {
        return kotlin.runCatching { RawPassword.create(value) }
    }

    override fun createRandomRawPassword(): RawPassword {
        return RawPassword.randomCreate()
    }

    override fun encode(rawPassword: RawPassword): EncodedPassword {
        return externalPasswordHandler.encode(rawPassword)
    }

    override fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean {
        return externalPasswordHandler.matches(rawPassword, encodedPassword)
    }
}
