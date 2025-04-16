package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort

class MemberPersistencePortFixture : MemberPersistencePort {

    private val storage = mutableMapOf<Long, Member>()

    override fun save(member: Member): Member {
        storage[member.memberId] = member
        return member
    }

    override fun findByIdOrNull(memberId: Long): Member? {
        return storage[memberId]
    }

    override fun findAuthUserOrNull(userId: Long): AuthUser? {
        return findByIdOrNull(userId)?.let { authUserFixture(it.memberId, it.role) }
    }

    override fun findByEmailOrNull(email: String): Member? {
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
