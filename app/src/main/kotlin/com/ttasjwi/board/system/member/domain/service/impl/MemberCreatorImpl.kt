package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.global.idgenerator.IdGenerator
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.MemberCreator
import com.ttasjwi.board.system.member.domain.service.PasswordManager

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
