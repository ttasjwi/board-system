package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.service.MemberAppender
import com.ttasjwi.board.system.member.domain.service.MemberFinder

class MemberStorageFixture : MemberAppender, MemberFinder {

    private val storage = mutableMapOf<Long, Member>()

    override fun save(member: Member): Member {
        storage[member.id] = member
        return member
    }

    override fun findByIdOrNull(id: Long): Member? {
        return storage[id]
    }

    override fun existsById(id: Long): Boolean {
        return storage.containsKey(id)
    }

    override fun findByEmailOrNull(email: String): Member? {
        return storage.values.firstOrNull { it.email == email }
    }

    override fun existsByEmail(email: String): Boolean {
        return storage.values.any { it.email == email }
    }

    override fun findByUsernameOrNull(username: Username): Member? {
        return storage.values.firstOrNull { it.username == username }
    }

    override fun existsByUsername(username: Username): Boolean {
        return storage.values.any { it.username == username }
    }

    override fun findByNicknameOrNull(nickname: Nickname): Member? {
        return storage.values.firstOrNull { it.nickname == nickname }
    }

    override fun existsByNickname(nickname: Nickname): Boolean {
        return storage.values.any { it.nickname == nickname }
    }

}
