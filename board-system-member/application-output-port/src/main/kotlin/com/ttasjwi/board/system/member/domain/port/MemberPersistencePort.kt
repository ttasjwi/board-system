package com.ttasjwi.board.system.member.domain.port

import com.ttasjwi.board.system.member.domain.model.Member

interface MemberPersistencePort {
    fun save(member: Member): Member
    fun findByIdOrNull(memberId: Long): Member?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
