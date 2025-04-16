package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaSocialConnection
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaSocialConnectionRepository
import org.springframework.stereotype.Component

@Component
class SocialConnectionPersistenceAdapter(
    private val jpaSocialConnectionRepository: JpaSocialConnectionRepository
) : SocialConnectionPersistencePort {

    override fun save(socialConnection: SocialConnection): SocialConnection {
        val jpaModel = JpaSocialConnection.from(socialConnection)
        jpaSocialConnectionRepository.save(jpaModel)
        return socialConnection
    }

    override fun findBySocialServiceUserOrNull(
        socialServiceUser: SocialServiceUser,
    ): SocialConnection? {
        return jpaSocialConnectionRepository.findBySocialServiceAndSocialServiceUserIdOrNull(
            socialService = socialServiceUser.socialService.name,
            socialServiceUserId = socialServiceUser.socialServiceUserId
        )?.restoreDomain()
    }
}
