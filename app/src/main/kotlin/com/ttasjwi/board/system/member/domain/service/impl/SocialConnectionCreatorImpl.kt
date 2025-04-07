package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.SocialConnectionCreator

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
