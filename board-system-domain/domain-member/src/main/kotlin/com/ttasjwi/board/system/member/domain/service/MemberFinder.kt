package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.Username

interface MemberFinder {

    fun findByIdOrNull(id: MemberId): Member?
    fun existsById(id: MemberId): Boolean

    fun findByEmailOrNull(email: Email): Member?
    fun existsByEmail(email: Email): Boolean

    fun findByUsernameOrNull(username: Username): Member?
    fun existsByUsername(username: Username): Boolean

    fun findByNicknameOrNull(nickname: Nickname): Member?
    fun existsByNickname(nickname: Nickname): Boolean
}
