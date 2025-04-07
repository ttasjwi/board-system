package com.ttasjwi.board.system.domain.member.service

interface NicknameManager {
    fun validate(nickname: String): Result<String>
    fun createRandom(): String
}
