package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.fixture.boardDescriptionFixture
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionCreator
import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture

class BoardDescriptionCreatorFixture : BoardDescriptionCreator {

    companion object {
        const val ERROR_DESCRIPTION = "INVALID_DESCRIPTION"
    }

    override fun create(value: String): Result<BoardDescription> {
        return kotlin.runCatching {
            if (value == ERROR_DESCRIPTION) {
                throw customExceptionFixture(
                    status = ErrorStatus.BAD_REQUEST,
                    code = "Error.InvalidBoardDescription",
                    args = emptyList(),
                    source = "boardDescription",
                    debugMessage = "게시판 설명 포맷 예외 - 테스트"
                )
            }
            boardDescriptionFixture(value)
        }
    }
}
