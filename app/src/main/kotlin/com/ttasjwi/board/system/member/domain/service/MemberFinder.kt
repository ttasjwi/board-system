package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.Username

interface MemberFinder {

    fun findByIdOrNull(id: Long): Member?
    fun existsById(id: Long): Boolean

    fun findByEmailOrNull(email: String): Member?
    fun existsByEmail(email: String): Boolean

    fun findByUsernameOrNull(username: Username): Member?
    fun existsByUsername(username: Username): Boolean

    fun findByNicknameOrNull(nickname: Nickname): Member?
    fun existsByNickname(nickname: Nickname): Boolean
}
