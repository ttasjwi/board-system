package com.ttasjwi.board.system.member.domain.service

interface UsernameManager {

    fun validate(username: String): Result<String>
    fun createRandom(): String
}
