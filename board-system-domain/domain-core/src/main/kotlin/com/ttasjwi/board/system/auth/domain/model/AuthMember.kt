package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.member.domain.model.*

abstract class AuthMember(
    val memberId: MemberId,
    val email: Email,
    val username: Username,
    val nickname: Nickname,
    val role: Role,
) {

    companion object {
        fun restore(
            memberId: Long,
            email: String,
            username: String,
            nickname: String,
            roleName: String
        ): AuthMember {
            return RestoredAuthMember(
                memberId = MemberId.restore(memberId),
                email = Email.restore(email),
                username = Username.restore(username),
                nickname = Nickname.restore(nickname),
                role = Role.restore(roleName)
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthMember) return false

        if (memberId != other.memberId) return false
        if (email != other.email) return false
        if (username != other.username) return false
        if (nickname != other.nickname) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = memberId.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + role.hashCode()
        return result
    }

    final override fun toString(): String {
        return "AuthMember(memberId=$memberId, email=$email, username=$username, nickname=$nickname, role=$role)"
    }

    private class RestoredAuthMember(
        memberId: MemberId,
        email: Email,
        username: Username,
        nickname: Nickname,
        role: Role,
    ) : AuthMember(
        memberId = memberId,
        email = email,
        username = username,
        nickname = nickname,
        role = role,
    )
}
