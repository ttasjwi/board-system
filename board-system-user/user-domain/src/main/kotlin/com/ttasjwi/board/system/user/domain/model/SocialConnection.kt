package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

/**
 * 소셜 연동
 */
class SocialConnection
internal constructor(
    val socialConnectionId: Long,
    val memberId: Long,
    val socialServiceUser: SocialServiceUser,
    val linkedAt: AppDateTime,
) {

    companion object {

        fun create(
            socialConnectionId: Long,
            memberId: Long,
            socialServiceUser: SocialServiceUser,
            currentTime: AppDateTime
        ): SocialConnection {
            return SocialConnection(
                socialConnectionId = socialConnectionId,
                memberId = memberId,
                socialServiceUser = socialServiceUser,
                linkedAt = currentTime,
            )
        }

        fun restore(
            socialConnectionId: Long,
            memberId: Long,
            socialServiceName: String,
            socialServiceUserId: String,
            linkedAt: LocalDateTime
        ): SocialConnection {
            return SocialConnection(
                socialConnectionId = socialConnectionId,
                memberId = memberId,
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                linkedAt = AppDateTime.from(linkedAt)
            )
        }
    }
}
