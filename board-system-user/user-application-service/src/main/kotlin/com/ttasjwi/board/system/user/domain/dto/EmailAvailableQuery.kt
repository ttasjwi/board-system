package com.ttasjwi.board.system.user.domain.dto

import java.util.*

internal data class EmailAvailableQuery(
    val email: String,
    val locale: Locale,
)
