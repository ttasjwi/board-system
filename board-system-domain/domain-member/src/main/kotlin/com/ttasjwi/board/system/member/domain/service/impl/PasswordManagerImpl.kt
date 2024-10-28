package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.service.PasswordManager

@DomainService
internal class PasswordManagerImpl : PasswordManager {

    override fun createRawPassword(value: String): Result<RawPassword> {
        TODO("Not yet implemented")
    }

    override fun encode(rawPassword: RawPassword): EncodedPassword {
        TODO("Not yet implemented")
    }

    override fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean {
        TODO("Not yet implemented")
    }

}
