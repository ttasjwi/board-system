package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaMember
import com.ttasjwi.board.system.member.domain.external.db.jpa.JpaMemberRepository
import com.ttasjwi.board.system.member.domain.model.*
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

        if (member.id == null) {
            val id = MemberId.restore(jpaMember.id!!)
            member.initId(id)
        }
        return member
    }

    override fun findByIdOrNull(id: MemberId): Member? {
        return jpaMemberRepository.findByIdOrNull(id.value)?.restoreDomain()
    }

    override fun existsById(id: MemberId): Boolean {
        return jpaMemberRepository.existsById(id.value)
    }

    override fun findByEmailOrNull(email: Email): Member? {
        return jpaMemberRepository.findByEmailOrNull(email.value)?.restoreDomain()
    }

    override fun existsByEmail(email: Email): Boolean {
        return jpaMemberRepository.existsByEmail(email.value)
    }

    override fun findByUsernameOrNull(username: Username): Member? {
        return jpaMemberRepository.findByUsernameOrNull(username.value)?.restoreDomain()
    }

    override fun existsByUsername(username: Username): Boolean {
        return jpaMemberRepository.existsByUsername(username.value)
    }

    override fun findByNicknameOrNull(nickname: Nickname): Member? {
        return jpaMemberRepository.findByNicknameOrNull(nickname.value)?.restoreDomain()
    }

    override fun existsByNickname(nickname: Nickname): Boolean {
        return jpaMemberRepository.existsByNickname(nickname.value)
    }

}
