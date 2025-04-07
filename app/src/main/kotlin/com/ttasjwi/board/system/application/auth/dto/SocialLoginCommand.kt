package com.ttasjwi.board.system.application.auth.dto

import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.SocialServiceUser

internal class SocialLoginCommand(
    val socialServiceUser: SocialServiceUser,
    val email: String,
    val currentTime: AppDateTime,
)
