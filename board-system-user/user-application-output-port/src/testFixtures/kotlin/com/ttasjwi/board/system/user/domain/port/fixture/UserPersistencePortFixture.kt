package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort

class UserPersistencePortFixture : UserPersistencePort {

    private val storage = mutableMapOf<Long, User>()

    override fun save(user: User): User {
        storage[user.userId] = user
        return user
    }

    override fun findByIdOrNull(userId: Long): User? {
        return storage[userId]
    }

    override fun findAuthUserOrNull(userId: Long): AuthUser? {
        return findByIdOrNull(userId)?.let { authUserFixture(it.userId, it.role) }
    }

    override fun findByEmailOrNull(email: String): User? {
        return storage.values.find { it.email == email }
    }

    override fun existsByEmail(email: String): Boolean {
        return storage.values.any { it.email == email }
    }

    override fun existsByUsername(username: String): Boolean {
        return storage.values.any { it.username == username }
    }

    override fun existsByNickname(nickname: String): Boolean {
        return storage.values.any { it.nickname == nickname }
    }
}
