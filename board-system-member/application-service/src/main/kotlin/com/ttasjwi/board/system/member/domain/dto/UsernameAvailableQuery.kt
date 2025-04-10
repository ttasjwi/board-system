package com.ttasjwi.board.system.member.domain.dto

import java.util.Locale

internal data class UsernameAvailableQuery(
    val username: String,
    val locale: Locale,
)
