package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.board.domain.service.BoardNameManager
import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture

class BoardNameManagerFixture : BoardNameManager {

    companion object {
        const val ERROR_NAME = "INVALID_NAME"
    }

    override fun validate(boardName: String): Result<String> = kotlin.runCatching {
        if (boardName == ERROR_NAME) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidBoardName",
                args = emptyList(),
                source = "boardName",
                debugMessage = "게시판 이름 포맷 예외 - 테스트"
            )
        }
        boardName
    }
}
