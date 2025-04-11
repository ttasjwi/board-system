package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaSocialConnection
import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaSocialConnectionRepository
import com.ttasjwi.board.system.member.domain.model.SocialConnection
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import com.ttasjwi.board.system.member.domain.service.SocialConnectionStorage
import org.springframework.stereotype.Component

@Component
class SocialConnectionStorageImpl(
    private val jpaSocialConnectionRepository: JpaSocialConnectionRepository
) : SocialConnectionStorage {

    override fun save(socialConnection: SocialConnection): SocialConnection {
        val jpaModel = JpaSocialConnection.from(socialConnection)
        jpaSocialConnectionRepository.save(jpaModel)
        return socialConnection
    }

    override fun findBySocialServiceUserOrNull(
        socialServiceUser: SocialServiceUser,
    ): SocialConnection? {
        return jpaSocialConnectionRepository.findBySocialServiceAndSocialServiceUserIdOrNull(
            socialService = socialServiceUser.service.name,
            socialServiceUserId = socialServiceUser.userId
        )?.toDomainEntity()
    }
}
