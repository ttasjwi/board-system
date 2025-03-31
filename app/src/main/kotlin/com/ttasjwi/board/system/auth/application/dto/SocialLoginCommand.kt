package com.ttasjwi.board.system.auth.application.dto

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser
import java.time.ZonedDateTime

internal class SocialLoginCommand(
    val socialServiceUser: SocialServiceUser,
    val email: Email,
    val currentTime: ZonedDateTime,
)
