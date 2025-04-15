package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class BoardNotFoundException(
    source: String,
    argument: Any,
) : CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.BoardNotFound",
    args = listOf(source, argument),
    source = source,
    debugMessage = "조건에 부합하는 게시판을 찾지 못 했습니다. ($source = $argument)"
)

