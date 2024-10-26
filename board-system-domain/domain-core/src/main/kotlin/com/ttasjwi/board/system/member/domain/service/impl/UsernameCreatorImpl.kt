package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.service.UsernameCreator

@DomainService
internal class UsernameCreatorImpl : UsernameCreator {

    override fun create(value: String): Result<Username> {
        TODO("Not yet implemented")
    }
}
