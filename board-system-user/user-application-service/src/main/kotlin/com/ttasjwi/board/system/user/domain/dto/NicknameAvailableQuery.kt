package com.ttasjwi.board.system.user.domain.dto

import java.util.Locale

internal data class NicknameAvailableQuery(
    val nickname: String,
    val locale: Locale,
)
