package com.ttasjwi.board.system.user.domain.port

interface EmailFormatValidatePort {

    fun validate(email: String): Result<String>
}
