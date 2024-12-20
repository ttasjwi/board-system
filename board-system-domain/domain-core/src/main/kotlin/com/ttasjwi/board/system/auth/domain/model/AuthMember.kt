package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.member.domain.model.*

abstract class AuthMember(
    val memberId: MemberId,
    val role: Role,
) {

    companion object {
        fun restore(
            memberId: Long,
            roleName: String
        ): AuthMember {
            return RestoredAuthMember(
                memberId = MemberId.restore(memberId),
                role = Role.restore(roleName)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthMember) return false
        if (memberId != other.memberId) return false
        if (role != other.role) return false
        return true
    }

    override fun hashCode(): Int {
        var result = memberId.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }

    final override fun toString(): String {
        return "AuthMember(memberId=$memberId, role=$role)"
    }

    private class RestoredAuthMember(
        memberId: MemberId,
        role: Role,
    ) : AuthMember(
        memberId = memberId,
        role = role,
    )
}
