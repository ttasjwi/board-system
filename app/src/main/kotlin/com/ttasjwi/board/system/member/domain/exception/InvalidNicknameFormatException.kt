package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.service.impl.NicknameManagerImpl

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
