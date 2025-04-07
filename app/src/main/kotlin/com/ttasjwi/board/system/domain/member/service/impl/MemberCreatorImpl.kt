package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.idgerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.member.model.Member
import com.ttasjwi.board.system.domain.member.service.MemberCreator
import com.ttasjwi.board.system.domain.member.service.PasswordManager

@DomainService
class MemberCreatorImpl(
    private val passwordManager: PasswordManager,
) : MemberCreator {

    private val idGenerator: IdGenerator = IdGenerator.create()

    override fun create(
        email: String,
        password: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): Member {
        return Member.create(
            id = idGenerator.nextId(),
            email = email,
            password = passwordManager.encode(password),
            username = username,
            nickname = nickname,
            registeredAt = currentTime,
        )
    }
}
