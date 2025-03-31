package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.auth.domain.model.Role
import java.time.ZonedDateTime

class Member
internal constructor(
    val id: Long,
    email: Email,
    password: EncodedPassword,
    username: Username,
    nickname: Nickname,
    role: Role,
    val registeredAt: ZonedDateTime,
) {

    var email: Email = email
        private set

    var password: EncodedPassword = password
        private set

    var username: Username = username
        private set

    var nickname: Nickname = nickname
        private set

    var role: Role = role
        private set

    companion object {

        /**
         * 가입 회원 생성
         */
        internal fun create(
            id: Long,
            email: Email,
            password: EncodedPassword,
            username: Username,
            nickname: Nickname,
            registeredAt: ZonedDateTime,
        ): Member {
            return Member(
                id = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                role = Role.USER,
                registeredAt = registeredAt,
            )
        }

        fun restore(
            id: Long,
            email: String,
            password: String,
            username: String,
            nickname: String,
            roleName: String,
            registeredAt: ZonedDateTime
        ): Member {
            return Member(
                id = id,
                email = Email.restore(email),
                password = EncodedPassword.restore(password),
                username = Username.restore(username),
                nickname = Nickname.restore(nickname),
                role = Role.restore(roleName),
                registeredAt = registeredAt
            )
        }

    }

    override fun toString(): String {
        return "Member(id=$id, email=$email, password=$password, username=$username, nickname=$nickname, role=$role, registeredAt=$registeredAt)"
    }
}
