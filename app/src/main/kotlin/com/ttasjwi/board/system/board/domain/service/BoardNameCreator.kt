package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.BoardName

interface BoardNameCreator {

    fun create(value: String): Result<BoardName>
}
