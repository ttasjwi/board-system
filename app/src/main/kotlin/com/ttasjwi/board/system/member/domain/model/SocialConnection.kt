package com.ttasjwi.board.system.member.domain.model

import java.time.ZonedDateTime

/**
 * 소셜 연동
 */
class SocialConnection
internal constructor(
    val id: Long,
    val memberId: Long,
    val socialServiceUser: SocialServiceUser,
    val linkedAt: ZonedDateTime,
) {

    companion object {

        internal fun create(
            id: Long,
            memberId: Long,
            socialServiceUser: SocialServiceUser,
            currentTime: ZonedDateTime
        ): SocialConnection {
            return SocialConnection(
                id = id,
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
                id = id,
                memberId = memberId,
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                linkedAt = linkedAt
            )
        }
    }
}
