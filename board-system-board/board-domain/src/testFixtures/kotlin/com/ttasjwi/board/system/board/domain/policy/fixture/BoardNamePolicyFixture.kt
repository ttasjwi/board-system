package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.BoardNamePolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class BoardNamePolicyFixture : BoardNamePolicy {

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
