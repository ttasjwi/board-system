package com.ttasjwi.board.system.email.domain.service

import com.ttasjwi.board.system.email.domain.model.Email

interface EmailCreator {

    fun create(value: String): Result<Email>
}
