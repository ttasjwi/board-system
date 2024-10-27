package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Email

interface EmailCreator {

    fun create(value: String): Result<Email>
}
