package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.Member

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

    override fun findByUsernameOrNull(username: String): Member? {
        return storage.values.firstOrNull { it.username == username }
    }

    override fun existsByUsername(username: String): Boolean {
        return storage.values.any { it.username == username }
    }

    override fun findByNicknameOrNull(nickname: String): Member? {
        return storage.values.firstOrNull { it.nickname == nickname }
    }

    override fun existsByNickname(nickname: String): Boolean {
        return storage.values.any { it.nickname == nickname }
    }
}
