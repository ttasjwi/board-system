package com.ttasjwi.board.system.member.domain.external.db

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.service.MemberAppender
import com.ttasjwi.board.system.member.domain.service.MemberFinder

@AppComponent
internal class MemberStorage : MemberAppender, MemberFinder {

    override fun save(member: Member): Member {
        TODO("Not yet implemented")
    }

    override fun findByIdOrNull(id: MemberId): Member? {
        TODO("Not yet implemented")
    }

    override fun existsById(id: MemberId): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByEmailOrNull(email: Email): Member? {
        TODO("Not yet implemented")
    }

    override fun existsByEmail(email: Email): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByUsernameOrNull(username: Username): Member? {
        TODO("Not yet implemented")
    }

    override fun existsByUsername(username: Username): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByNicknameOrNull(nickname: Nickname): Member? {
        TODO("Not yet implemented")
    }

    override fun existsByNickname(nickname: Nickname): Boolean {
        TODO("Not yet implemented")
    }
}
