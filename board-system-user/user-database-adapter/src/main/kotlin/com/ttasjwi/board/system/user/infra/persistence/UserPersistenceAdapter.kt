package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaUser
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaUserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val jpaUserRepository: JpaUserRepository
) : UserPersistencePort {

    override fun save(user: User): User {
        val jpaUser = JpaUser.from(user)
        jpaUserRepository.save(jpaUser)
        return user
    }

    override fun findByIdOrNull(userId: Long): User? {
        return jpaUserRepository.findByIdOrNull(userId)?.restoreDomain()
    }

    override fun findAuthUserOrNull(userId: Long): AuthUser? {
        return jpaUserRepository.findAuthUserProjectionOrNull(userId)?.let {
            AuthUser.restore(it.getUserId(), it.getRole())
        }
    }

    override fun findByEmailOrNull(email: String): User? {
        return jpaUserRepository.findByEmailOrNull(email)?.restoreDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return jpaUserRepository.existsByEmail(email)
    }

    override fun existsByUsername(username: String): Boolean {
        return jpaUserRepository.existsByUsername(username)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return jpaUserRepository.existsByNickname(nickname)
    }
}
