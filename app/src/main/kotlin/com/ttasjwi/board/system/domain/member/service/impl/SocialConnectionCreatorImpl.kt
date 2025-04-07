package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.domain.member.model.SocialConnection
import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import com.ttasjwi.board.system.domain.member.service.SocialConnectionCreator
import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.global.idgenerator.IdGenerator
import com.ttasjwi.board.system.global.time.AppDateTime

@DomainService
class SocialConnectionCreatorImpl : SocialConnectionCreator {

    private val idGenerator: IdGenerator = IdGenerator.create()

    override fun create(
        memberId: Long,
        socialServiceUser: SocialServiceUser,
        currentTime: AppDateTime
    ): SocialConnection {
        return SocialConnection.create(
            id = idGenerator.nextId(),
            memberId = memberId,
            socialServiceUser = socialServiceUser,
            currentTime = currentTime
        )
    }
}
