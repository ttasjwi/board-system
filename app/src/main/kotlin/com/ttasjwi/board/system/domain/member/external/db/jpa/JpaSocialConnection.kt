package com.ttasjwi.board.system.domain.member.external.db.jpa

import com.ttasjwi.board.system.domain.member.model.SocialConnection
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "social_connections")
class JpaSocialConnection(

    @Id
    @Column(name = "social_connection_id")
    val id: Long,

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
                id = socialConnection.id,
                memberId = socialConnection.memberId,
                socialService = socialConnection.socialServiceUser.service.name,
                socialServiceUserId = socialConnection.socialServiceUser.userId,
                linkedAt = socialConnection.linkedAt.toLocalDateTime()
            )
        }
    }

    fun toDomainEntity(): SocialConnection {
        return SocialConnection.restore(
            id = this.id,
            memberId = this.memberId,
            socialServiceName = this.socialService,
            socialServiceUserId = this.socialServiceUserId,
            linkedAt = this.linkedAt
        )
    }
}
