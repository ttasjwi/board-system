package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.core.domain.model.DomainEntity
import java.time.ZonedDateTime

/**
 * 소셜 연동
 */
class SocialConnection
internal constructor(
    id: SocialConnectionId? = null,
    val memberId: MemberId,
    val socialServiceUser: SocialServiceUser,
    val linkedAt: ZonedDateTime,
) : DomainEntity<SocialConnectionId>(id) {

    companion object {

        internal fun create(
            memberId: MemberId,
            socialServiceUser: SocialServiceUser,
            currentTime: ZonedDateTime
        ): SocialConnection {
            return SocialConnection(
                memberId = memberId,
                socialServiceUser = socialServiceUser,
                linkedAt = currentTime,
            )
        }

        fun restore(
            id: Long,
            memberId: Long,
            socialServiceName: String,
            socialServiceUserId: String,
            linkedAt: ZonedDateTime
        ): SocialConnection {
            return SocialConnection(
                id = SocialConnectionId.restore(id),
                memberId = MemberId.restore(memberId),
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                linkedAt = linkedAt
            )
        }
    }
}
