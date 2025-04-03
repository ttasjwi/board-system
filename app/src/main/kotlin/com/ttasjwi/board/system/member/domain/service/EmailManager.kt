package com.ttasjwi.board.system.member.domain.service

interface EmailManager {
    fun validate(email: String): Result<String>
}
