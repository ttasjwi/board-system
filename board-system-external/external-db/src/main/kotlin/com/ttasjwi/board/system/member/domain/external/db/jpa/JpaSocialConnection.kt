package com.ttasjwi.board.system.member.domain.external.db.jpa

import com.ttasjwi.board.system.core.time.TimeRule
import com.ttasjwi.board.system.member.domain.model.*
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "social_connections")
class JpaSocialConnection(

    @Id
    @Column(name = "social_connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "social_service")
    val socialService: String,

    @Column(name = "social_service_user_id")
    val socialServiceUserId: String,

    @Column(name = "linked_at")
    val linkedAt: ZonedDateTime
) {


    companion object {

        fun from(socialConnection: SocialConnection): JpaSocialConnection {
            return JpaSocialConnection(
                id = socialConnection.id?.value,
                memberId = socialConnection.memberId.value,
                socialService = socialConnection.socialServiceUser.service.name,
                socialServiceUserId = socialConnection.socialServiceUser.userId,
                linkedAt = socialConnection.linkedAt
            )
        }
    }

    fun toDomainEntity(): SocialConnection {
        return SocialConnection.restore(
            id = this.id!!,
            memberId = this.memberId,
            socialServiceName = this.socialService,
            socialServiceUserId = this.socialServiceUserId,
            linkedAt = this.linkedAt.withZoneSameInstant(TimeRule.ZONE_ID)
        )
    }
}
