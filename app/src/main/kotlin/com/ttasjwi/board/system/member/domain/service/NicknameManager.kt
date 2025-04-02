package com.ttasjwi.board.system.member.domain.service

interface NicknameManager {
    fun validate(nickname: String): Result<String>
    fun createRandom(): String
}
