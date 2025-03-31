package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.fixture.boardNameFixture
import com.ttasjwi.board.system.board.domain.service.BoardNameCreator
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class BoardNameCreatorFixture : BoardNameCreator {

    companion object {
        const val ERROR_NAME = "INVALID_NAME"
    }

    override fun create(value: String): Result<BoardName> {
        return kotlin.runCatching {
            if (value == ERROR_NAME) {
                throw customExceptionFixture(
                    status = ErrorStatus.BAD_REQUEST,
                    code = "Error.InvalidBoardName",
                    args = emptyList(),
                    source = "boardName",
                    debugMessage = "게시판 이름 포맷 예외 - 테스트"
                )
            }
            boardNameFixture(value)
        }
    }
}
