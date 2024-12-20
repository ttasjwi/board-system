package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.Username
import java.time.ZonedDateTime

internal data class RegisterMemberCommand(
    val email: Email,
    val rawPassword: RawPassword,
    val username: Username,
    val nickname: Nickname,
    val currentTime: ZonedDateTime,
)
