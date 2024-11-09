package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.core.domain.model.DomainEntity
import java.time.ZonedDateTime

class Member
internal constructor(
    id: MemberId? = null,
    email: Email,
    password: EncodedPassword,
    username: Username,
    nickname: Nickname,
    role: Role,
    val registeredAt: ZonedDateTime,
) : DomainEntity<MemberId>(id) {

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
            email: Email,
            password: EncodedPassword,
            username: Username,
            nickname: Nickname,
            registeredAt: ZonedDateTime,
        ): Member {
            return Member(
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
                id = MemberId.restore(id),
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
