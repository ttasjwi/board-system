package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.global.time.AppDateTime
import java.time.LocalDateTime

/**
 * 소셜 연동
 */
class SocialConnection
internal constructor(
    val id: Long,
    val memberId: Long,
    val socialServiceUser: SocialServiceUser,
    val linkedAt: AppDateTime,
) {

    companion object {

        internal fun create(
            id: Long,
            memberId: Long,
            socialServiceUser: SocialServiceUser,
            currentTime: AppDateTime
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
            linkedAt: LocalDateTime
        ): SocialConnection {
            return SocialConnection(
                id = id,
                memberId = memberId,
                socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId),
                linkedAt = AppDateTime.from(linkedAt)
            )
        }
    }
}
