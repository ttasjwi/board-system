package com.ttasjwi.board.system.member.domain.policy

interface EmailFormatPolicy {
    fun validate(email: String): Result<String>
}
