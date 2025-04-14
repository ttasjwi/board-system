package com.ttasjwi.board.system.board.domain.policy

interface BoardDescriptionPolicy {
    fun create(boardDescription: String): Result<String>
}
