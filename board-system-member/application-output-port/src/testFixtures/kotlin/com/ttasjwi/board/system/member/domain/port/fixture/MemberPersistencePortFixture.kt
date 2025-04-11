package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort

class MemberPersistencePortFixture : MemberPersistencePort {

    private val storage = mutableMapOf<Long, Member>()

    override fun save(member: Member): Member {
        storage[member.memberId] = member
        return member
    }

    override fun findByIdOrNull(memberId: Long): Member? {
        return storage[memberId]
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
