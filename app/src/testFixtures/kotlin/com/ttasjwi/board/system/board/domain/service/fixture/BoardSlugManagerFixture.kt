package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.service.BoardSlugManager
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class BoardSlugManagerFixture : BoardSlugManager {

    companion object {
        const val ERROR_SLUG = "INVALID_SLUG"
    }

    override fun validate(boardSlug: String): Result<String> = kotlin.runCatching {
        if (boardSlug == ERROR_SLUG) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidBoardSlug",
                args = emptyList(),
                source = "boardSlug",
                debugMessage = "게시판 슬러그 포맷 예외 - 테스트"
            )
        }
        boardSlug
    }
}
