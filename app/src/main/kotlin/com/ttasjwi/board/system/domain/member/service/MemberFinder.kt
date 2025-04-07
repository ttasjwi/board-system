package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.Member

interface MemberFinder {

    fun findByIdOrNull(id: Long): Member?
    fun existsById(id: Long): Boolean

    fun findByEmailOrNull(email: String): Member?
    fun existsByEmail(email: String): Boolean

    fun findByUsernameOrNull(username: String): Member?
    fun existsByUsername(username: String): Boolean

    fun findByNicknameOrNull(nickname: String): Member?
    fun existsByNickname(nickname: String): Boolean
}
