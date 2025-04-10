package com.ttasjwi.board.system.member.domain.policy

interface UsernamePolicy {
    fun validate(username: String): Result<String>
    fun createRandom(): String
}
