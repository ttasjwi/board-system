package com.ttasjwi.board.system.user.infra.persistence.jpa

import com.ttasjwi.board.system.user.domain.model.*
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "social_connections")
class JpaSocialConnection(

    @Id
    @Column(name = "social_connection_id")
    val socialConnectionId: Long,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "social_service")
    val socialService: String,

    @Column(name = "social_service_user_id")
    val socialServiceUserId: String,

    @Column(name = "linked_at")
    val linkedAt: LocalDateTime
) {


    companion object {

        fun from(socialConnection: SocialConnection): JpaSocialConnection {
            return JpaSocialConnection(
                socialConnectionId = socialConnection.socialConnectionId,
                memberId = socialConnection.memberId,
                socialService = socialConnection.socialServiceUser.socialService.name,
                socialServiceUserId = socialConnection.socialServiceUser.socialServiceUserId,
                linkedAt = socialConnection.linkedAt.toLocalDateTime()
            )
        }
    }

    internal fun restoreDomain(): SocialConnection {
        return SocialConnection.restore(
            socialConnectionId = this.socialConnectionId,
            memberId = this.memberId,
            socialServiceName = this.socialService,
            socialServiceUserId = this.socialServiceUserId,
            linkedAt = this.linkedAt
        )
    }
}
