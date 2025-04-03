package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class Member
internal constructor(
    val id: Long,
    email: String,
    password: String,
    username: String,
    nickname: String,
    role: Role,
    val registeredAt: AppDateTime,
) {

    var email: String = email
        private set

    var password: String = password
        private set

    var username: String = username
        private set

    var nickname: String = nickname
        private set

    var role: Role = role
        private set

    companion object {

        /**
         * 가입 회원 생성
         */
        internal fun create(
            id: Long,
            email: String,
            password: String,
            username: String,
            nickname: String,
            registeredAt: AppDateTime,
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
            registeredAt: LocalDateTime
        ): Member {
            return Member(
                id = id,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                role = Role.restore(roleName),
                registeredAt = AppDateTime.from(registeredAt)
            )
        }

    }

    override fun toString(): String {
        return "Member(id=$id, email=$email, password=[!!SECRET!!], username=$username, nickname=$nickname, role=$role, registeredAt=$registeredAt)"
    }
}
