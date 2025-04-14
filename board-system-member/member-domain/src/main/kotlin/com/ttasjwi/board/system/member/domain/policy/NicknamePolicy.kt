package com.ttasjwi.board.system.member.domain.policy

interface NicknamePolicy {
    fun validate(nickname: String): Result<String>
    fun createRandom(): String
}
