package com.ttasjwi.board.system.member.application.dto

import com.ttasjwi.board.system.member.domain.model.Email
import java.time.ZonedDateTime
import java.util.*

internal data class EmailVerificationStartCommand(
    val email: Email,
    val currenTime: ZonedDateTime,
    val locale: Locale,
)
