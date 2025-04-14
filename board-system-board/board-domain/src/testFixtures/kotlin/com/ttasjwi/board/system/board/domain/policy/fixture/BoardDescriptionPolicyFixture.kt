package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.BoardDescriptionPolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class BoardDescriptionPolicyFixture : BoardDescriptionPolicy {

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

