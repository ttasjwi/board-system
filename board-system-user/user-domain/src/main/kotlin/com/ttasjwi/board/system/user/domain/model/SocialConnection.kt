package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

/**
 * 소셜 연동
 */
class SocialConnection
internal constructor(
    val socialConnectionId: Long,
    val socialService: SocialService,
    val socialServiceUserId: String,
    val userId: Long,
    val linkedAt: AppDateTime,
) {

    companion object {

        fun create(
            socialConnectionId: Long,
            userId: Long,
            socialService: SocialService,
            socialServiceUserId: String,
            currentTime: AppDateTime
        ): SocialConnection {
            return SocialConnection(
                socialConnectionId = socialConnectionId,
                userId = userId,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = currentTime,
            )
        }

        fun restore(
            socialConnectionId: Long,
            socialServiceName: String,
            socialServiceUserId: String,
            userId: Long,
            linkedAt: LocalDateTime
        ): SocialConnection {
            return SocialConnection(
                socialConnectionId = socialConnectionId,
                socialService = SocialService.restore(socialServiceName),
                socialServiceUserId = socialServiceUserId,
                userId = userId,
                linkedAt = AppDateTime.from(linkedAt)
            )
        }
    }

    override fun toString(): String {
        return "SocialConnection(socialConnectionId=$socialConnectionId, socialService=$socialService, socialServiceUserId=$socialServiceUserId, userId=$userId, linkedAt=$linkedAt)"
    }
}
