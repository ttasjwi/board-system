package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.model.SocialService
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

    override fun read(socialService: SocialService, socialServiceUserId: String): SocialConnection? {
        return jpaSocialConnectionRepository.findBySocialServiceAndSocialServiceUserIdOrNull(
            socialService = socialService.name,
            socialServiceUserId = socialServiceUserId
        )?.restoreDomain()
    }
}
