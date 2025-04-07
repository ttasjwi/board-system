package com.ttasjwi.board.system.domain.member.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.time.AppDateTime

class EmailVerificationExpiredException(
    email: String,
    expiredAt: AppDateTime,
    currentTime: AppDateTime
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.EmailVerificationExpired",
    source = "emailVerification",
    args = listOf(email, expiredAt, currentTime),
    debugMessage = "이메일 인증이 만료됐습니다. 다시 인증해주세요. (email=$email, expiredAt=$expiredAt, currentTime=$currentTime)"
)
