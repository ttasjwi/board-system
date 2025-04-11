package com.ttasjwi.board.system.board.domain.service

interface BoardNameManager {
    fun validate(boardName: String): Result<String>
}
