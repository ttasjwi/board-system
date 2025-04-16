package com.ttasjwi.board.system.common.auth

class AuthUser
internal constructor(
    val userId: Long,
    val role: Role,
) {

    companion object {

        fun create(
            userId: Long,
            role: Role
        ): AuthUser {
            return AuthUser(
                userId = userId,
                role = role,
            )
        }

        fun restore(
            userId: Long,
            roleName: String
        ): AuthUser {
            return AuthUser(
                userId = userId,
                role = Role.restore(roleName)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthUser) return false
        if (userId != other.userId) return false
        if (role != other.role) return false
        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }

    override fun toString(): String {
        return "AuthMember(memberId=$userId, role=$role)"
    }
}
