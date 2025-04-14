package com.ttasjwi.board.system.board.domain.policy

interface BoardNamePolicy {
    fun validate(boardName: String): Result<String>
}
