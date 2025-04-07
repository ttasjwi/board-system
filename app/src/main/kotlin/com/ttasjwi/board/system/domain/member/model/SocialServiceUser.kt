package com.ttasjwi.board.system.domain.member.model

class SocialServiceUser
internal constructor(
    val service: SocialService,
    val userId: String,
) {

    companion object {
        fun restore(serviceName: String, serviceUserId: String): SocialServiceUser {
            return SocialServiceUser(
                service = SocialService.restore(serviceName),
                userId = serviceUserId
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SocialServiceUser) return false

        if (service != other.service) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = service.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }

    override fun toString(): String {
        return "SocialServiceUser(service=$service, userId='$userId')"
    }
}
