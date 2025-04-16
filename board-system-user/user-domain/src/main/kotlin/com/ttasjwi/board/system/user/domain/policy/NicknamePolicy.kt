package com.ttasjwi.board.system.user.domain.policy

interface NicknamePolicy {
    fun validate(nickname: String): Result<String>
    fun createRandom(): String
}
