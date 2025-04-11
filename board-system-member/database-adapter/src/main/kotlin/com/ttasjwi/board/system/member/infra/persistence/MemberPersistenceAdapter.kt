package com.ttasjwi.board.system.member.infra.persistence

import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.member.infra.persistence.jpa.JpaMember
import com.ttasjwi.board.system.member.infra.persistence.jpa.JpaMemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberPersistenceAdapter(
    private val jpaMemberRepository: JpaMemberRepository
) : MemberPersistencePort {

    override fun save(member: Member): Member {
        val jpaMember = JpaMember.from(member)
        jpaMemberRepository.save(jpaMember)
        return member
    }

    override fun findByIdOrNull(memberId: Long): Member? {
        return jpaMemberRepository.findByIdOrNull(memberId)?.restoreDomain()
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
