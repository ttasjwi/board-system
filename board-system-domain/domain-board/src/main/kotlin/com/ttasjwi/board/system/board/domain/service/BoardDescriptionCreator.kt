package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.BoardDescription

interface BoardDescriptionCreator {

    fun create(value: String): Result<BoardDescription>
}
