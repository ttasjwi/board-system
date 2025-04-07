package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.board.domain.service.BoardDescriptionManager
import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture

class BoardDescriptionManagerFixture : BoardDescriptionManager {

    companion object {
        const val ERROR_DESCRIPTION = "INVALID_DESCRIPTION"
    }

    override fun create(boardDescription: String): Result<String> = kotlin.runCatching {

        if (boardDescription == ERROR_DESCRIPTION) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidBoardDescription",
                args = emptyList(),
                source = "boardDescription",
                debugMessage = "게시판 설명 포맷 예외 - 테스트"
            )
        }
        boardDescription
    }
}

