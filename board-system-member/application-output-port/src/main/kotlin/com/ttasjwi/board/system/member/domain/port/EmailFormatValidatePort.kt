package com.ttasjwi.board.system.member.domain.port

interface EmailFormatValidatePort {
    fun validate(email: String): Result<String>
}
