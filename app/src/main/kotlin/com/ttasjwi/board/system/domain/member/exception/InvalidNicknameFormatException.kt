package com.ttasjwi.board.system.domain.member.exception

import com.ttasjwi.board.system.domain.member.service.impl.NicknameManagerImpl
import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class InvalidNicknameFormatException(
    nicknameValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidNicknameFormat",
    args = listOf(NicknameManagerImpl.MIN_LENGTH, NicknameManagerImpl.MAX_LENGTH, nicknameValue),
    source = "nickname",
    debugMessage = "" +
            "닉네임은 ${NicknameManagerImpl.MIN_LENGTH}자 이상, " +
            "${NicknameManagerImpl.MAX_LENGTH}자 이하의 한글/영어/숫자로만 구성되어야 합니다. " +
            "(nickname= $nicknameValue)"
)
