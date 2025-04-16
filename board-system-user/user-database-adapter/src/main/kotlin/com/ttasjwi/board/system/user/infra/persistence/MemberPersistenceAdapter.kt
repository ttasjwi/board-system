package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaUser
import com.ttasjwi.board.system.user.infra.persistence.jpa.JpaMemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val jpaMemberRepository: JpaMemberRepository
) : MemberPersistencePort {

    override fun save(member: Member): Member {
        val jpaUser = JpaUser.from(member)
        jpaMemberRepository.save(jpaUser)
        return member
    }

    override fun findByIdOrNull(memberId: Long): Member? {
        return jpaMemberRepository.findByIdOrNull(memberId)?.restoreDomain()
    }

    override fun findAuthUserOrNull(userId: Long): AuthUser? {
        return jpaMemberRepository.findAuthUserProjectionOrNull(userId)?.let {
            AuthUser.restore(it.getUserId(), it.getRole())
        }
    }

    override fun findByEmailOrNull(email: String): Member? {
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
