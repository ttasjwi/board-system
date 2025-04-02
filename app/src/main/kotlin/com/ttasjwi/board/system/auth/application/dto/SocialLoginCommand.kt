package com.ttasjwi.board.system.auth.application.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

internal class SocialLoginCommand(
    val socialServiceUser: SocialServiceUser,
    val email: Email,
    val currentTime: AppDateTime,
)
