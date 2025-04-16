package com.ttasjwi.board.system.user.domain.dto

import com.ttasjwi.board.system.common.time.AppDateTime
import java.util.*

internal data class EmailVerificationStartCommand(
    val email: String,
    val currenTime: AppDateTime,
    val locale: Locale,
)
