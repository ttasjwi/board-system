package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaMember
import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaMemberRepository
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.service.MemberAppender
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberStorageImpl(
    private val jpaMemberRepository: JpaMemberRepository
) : MemberAppender, MemberFinder {

    override fun save(member: Member): Member {
        val jpaMember = JpaMember.from(member)
        jpaMemberRepository.save(jpaMember)
        return member
    }

    override fun findByIdOrNull(id: Long): Member? {
        return jpaMemberRepository.findByIdOrNull(id)?.restoreDomain()
    }

    override fun existsById(id: Long): Boolean {
        return jpaMemberRepository.existsById(id)
    }

    override fun findByEmailOrNull(email: String): Member? {
        return jpaMemberRepository.findByEmailOrNull(email)?.restoreDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return jpaMemberRepository.existsByEmail(email)
    }

    override fun findByUsernameOrNull(username: String): Member? {
        return jpaMemberRepository.findByUsernameOrNull(username)?.restoreDomain()
    }

    override fun existsByUsername(username: String): Boolean {
        return jpaMemberRepository.existsByUsername(username)
    }

    override fun findByNicknameOrNull(nickname: Nickname): Member? {
        return jpaMemberRepository.findByNicknameOrNull(nickname.value)?.restoreDomain()
    }

    override fun existsByNickname(nickname: Nickname): Boolean {
        return jpaMemberRepository.existsByNickname(nickname.value)
    }

}
