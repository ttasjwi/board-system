package com.ttasjwi.board.system.common.auth

class AuthMember
internal constructor(
    val memberId: Long,
    val role: Role,
) {

    companion object {

        fun create(
            memberId: Long,
            role: Role
        ): AuthMember {
            return AuthMember(
                memberId = memberId,
                role = role,
            )
        }

        fun restore(
            memberId: Long,
            roleName: String
        ): AuthMember {
            return AuthMember(
                memberId = memberId,
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

    override fun toString(): String {
        return "AuthMember(memberId=$memberId, role=$role)"
    }
}
