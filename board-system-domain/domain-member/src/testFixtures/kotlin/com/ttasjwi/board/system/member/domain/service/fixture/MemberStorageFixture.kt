package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.member.domain.model.*
import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import com.ttasjwi.board.system.member.domain.service.MemberAppender
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import java.util.concurrent.atomic.AtomicLong

class MemberStorageFixture : MemberAppender, MemberFinder {

    private val storage = mutableMapOf<MemberId, Member>()
    private val sequence = AtomicLong(0)

    override fun save(member: Member): Member {
        if (member.id == null) {
            val id = memberIdFixture(sequence.incrementAndGet())
            member.initId(id)
        }
        storage[member.id!!] = member
        return member
    }

    override fun findByIdOrNull(id: MemberId): Member? {
        return storage[id]
    }

    override fun existsById(id: MemberId): Boolean {
        return storage.containsKey(id)
    }

    override fun findByEmailOrNull(email: Email): Member? {
        return storage.values.firstOrNull { it.email == email }
    }

    override fun existsByEmail(email: Email): Boolean {
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
