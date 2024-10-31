package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.service.NicknameCreator

@DomainService
internal class NicknameCreatorImpl : NicknameCreator {

    override fun create(value: String): Result<Nickname> {
        return kotlin.runCatching {
            Nickname.create(value)
        }
    }
}
