package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import com.ttasjwi.board.system.board.domain.service.BoardSlugCreator
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class BoardSlugCreatorFixture : BoardSlugCreator {

    companion object {
        const val ERROR_SLUG = "INVALID_SLUG"
    }

    override fun create(value: String): Result<BoardSlug> {
        return kotlin.runCatching {
            if (value == ERROR_SLUG) {
                throw customExceptionFixture(
                    status = ErrorStatus.BAD_REQUEST,
                    code = "Error.InvalidBoardSlug",
                    args = emptyList(),
                    source = "boardSlug",
                    debugMessage = "게시판 슬러그 포맷 예외 - 테스트"
                )
            }
            boardSlugFixture(value)
        }
    }
}
