package com.ttasjwi.board.system.domain.member.service

interface EmailManager {
    fun validate(email: String): Result<String>
}
