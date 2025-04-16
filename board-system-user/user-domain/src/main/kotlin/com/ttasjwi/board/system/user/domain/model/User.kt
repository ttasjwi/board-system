package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.LocalDateTime

class User
internal constructor(
    val userId: Long,
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
        fun create(
            userId: Long,
            email: String,
            password: String,
            username: String,
            nickname: String,
            registeredAt: AppDateTime,
        ): User {
            return User(
                userId = userId,
                email = email,
                password = password,
                username = username,
                nickname = nickname,
                role = Role.USER,
                registeredAt = registeredAt,
            )
        }

        fun restore(
            userId: Long,
            email: String,
            password: String,
            username: String,
            nickname: String,
            roleName: String,
            registeredAt: LocalDateTime
        ): User {
            return User(
                userId = userId,
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
        return "User(userId=$userId, email=$email, password=[!!SECRET!!], username=$username, nickname=$nickname, role=$role, registeredAt=$registeredAt)"
    }
}
