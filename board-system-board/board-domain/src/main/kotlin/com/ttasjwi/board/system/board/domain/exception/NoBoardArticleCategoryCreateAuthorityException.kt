package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class NoBoardArticleCategoryCreateAuthorityException(
    boardManagerId: Long,
    creatorId: Long,
) : CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.NoBoardArticleCategoryCreateAuthority",
    source = "boardArticleCreateAuthority",
    args = emptyList(),
    debugMessage = "카테고리를 추가할 권한이 없습니다. 게시판의 관리자가 아닙니다. (boardManagerId = $boardManagerId, creatorId = $creatorId)",
)
