package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.user.domain.model.User

interface UserPersistencePort {
    fun save(user: User): User
    fun findByIdOrNull(userId: Long): User?
    fun findAuthUserOrNull(userId: Long): AuthUser?
    fun findByEmailOrNull(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
