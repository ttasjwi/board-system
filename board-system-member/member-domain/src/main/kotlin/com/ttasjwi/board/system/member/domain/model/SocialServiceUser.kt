package com.ttasjwi.board.system.member.domain.model

class SocialServiceUser
internal constructor(
    val socialService: SocialService,
    val socialServiceUserId: String,
) {

    companion object {
        fun restore(serviceName: String, socialServiceUserId: String): SocialServiceUser {
            return SocialServiceUser(
                socialService = SocialService.restore(serviceName),
                socialServiceUserId = socialServiceUserId
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SocialServiceUser) return false

        if (socialService != other.socialService) return false
        if (socialServiceUserId != other.socialServiceUserId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = socialService.hashCode()
        result = 31 * result + socialServiceUserId.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocialServiceUser(socialService=$socialService, socialServiceUserId='$socialServiceUserId')"
    }
}
