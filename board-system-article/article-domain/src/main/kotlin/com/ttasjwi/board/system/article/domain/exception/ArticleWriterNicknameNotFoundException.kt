package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleWriterNicknameNotFoundException(
    source: String,
    argument: Any,
) : CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleWriterNicknameNotFound",
    source = source,
    args = listOf(source, argument),
    debugMessage = "게시글 작성을 시도했으나, 사용자 닉네임을 조회하는데 실패했습니다. 사용자가 탈퇴했을 가능성이 있습니다. ($source = $argument)",
)
