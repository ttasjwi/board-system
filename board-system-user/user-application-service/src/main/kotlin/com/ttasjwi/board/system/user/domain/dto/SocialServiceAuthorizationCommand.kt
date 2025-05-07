package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime

data class SocialServiceAuthorizationCommand(
    val oAuth2ClientRegistrationId: String,
    val currentTime: AppDateTime,
)
