package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaUser
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaMemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val jpaMemberRepository: JpaMemberRepository
) : MemberPersistencePort {

    override fun save(user: User): User {
        val jpaUser = JpaUser.from(user)
        jpaMemberRepository.save(jpaUser)
        return user
    }

    override fun findByIdOrNull(memberId: Long): User? {
        return jpaMemberRepository.findByIdOrNull(memberId)?.restoreDomain()
    }

    override fun findAuthUserOrNull(userId: Long): AuthUser? {
        return jpaMemberRepository.findAuthUserProjectionOrNull(userId)?.let {
            AuthUser.restore(it.getUserId(), it.getRole())
        }
    }

    override fun findByEmailOrNull(email: String): User? {
        return jpaMemberRepository.findByEmailOrNull(email)?.restoreDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return jpaMemberRepository.existsByEmail(email)
    }

    override fun existsByUsername(username: String): Boolean {
        return jpaMemberRepository.existsByUsername(username)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return jpaMemberRepository.existsByNickname(nickname)
    }
}
