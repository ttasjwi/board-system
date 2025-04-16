package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.user.domain.model.Member

interface MemberPersistencePort {
    fun save(member: Member): Member
    fun findByIdOrNull(memberId: Long): Member?
    fun findAuthUserOrNull(userId: Long): AuthUser?
    fun findByEmailOrNull(email: String): Member?
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
