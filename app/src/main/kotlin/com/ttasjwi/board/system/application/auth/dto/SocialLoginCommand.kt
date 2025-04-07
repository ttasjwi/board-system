package com.ttasjwi.board.system.application.auth.dto

import com.ttasjwi.board.system.domain.member.model.SocialServiceUser
import com.ttasjwi.board.system.global.time.AppDateTime

internal class SocialLoginCommand(
    val socialServiceUser: SocialServiceUser,
    val email: String,
    val currentTime: AppDateTime,
)
