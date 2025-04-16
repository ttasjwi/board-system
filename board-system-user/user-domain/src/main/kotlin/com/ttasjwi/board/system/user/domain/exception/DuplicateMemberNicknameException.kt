package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateMemberNicknameException(
    nickname: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateMemberNickname",
    args = listOf(nickname),
    source = "nickname",
    debugMessage = "중복되는 닉네임의 회원이 존재합니다.(nickname=${nickname})"
)
