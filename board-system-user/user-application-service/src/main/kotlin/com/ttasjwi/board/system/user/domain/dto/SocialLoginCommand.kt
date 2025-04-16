package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.SocialServiceUser

internal class SocialLoginCommand(
    val socialServiceUser: SocialServiceUser,
    val email: String,
    val currentTime: AppDateTime,
)
