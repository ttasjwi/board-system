package com.ttasjwi.board.system.domain.member.service

interface UsernameManager {

    fun validate(username: String): Result<String>
    fun createRandom(): String
}
