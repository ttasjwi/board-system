package com.ttasjwi.board.system.user.domain.policy

interface UsernamePolicy {
    fun validate(username: String): Result<String>
    fun createRandom(): String
}
