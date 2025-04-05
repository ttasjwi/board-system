package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.global.time.AppDateTime
import java.util.*

internal data class EmailVerificationStartCommand(
    val email: String,
    val currenTime: AppDateTime,
    val locale: Locale,
)
