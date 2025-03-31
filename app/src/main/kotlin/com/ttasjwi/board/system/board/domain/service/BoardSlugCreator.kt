package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.BoardSlug

interface BoardSlugCreator {

    fun create(value: String): Result<BoardSlug>
}
