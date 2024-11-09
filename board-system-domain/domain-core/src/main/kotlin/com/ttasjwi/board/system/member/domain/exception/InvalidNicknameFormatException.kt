package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.model.Nickname

class InvalidNicknameFormatException(
    nicknameValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidNicknameFormat",
    args = listOf(Nickname.MIN_LENGTH, Nickname.MAX_LENGTH, nicknameValue),
    source = "nickname",
    debugMessage = "" +
            "닉네임은 ${Nickname.MIN_LENGTH}자 이상, " +
            "${Nickname.MAX_LENGTH}자 이하의 한글/영어/숫자로만 구성되어야 합니다. " +
            "(nickname= $nicknameValue)"
)
