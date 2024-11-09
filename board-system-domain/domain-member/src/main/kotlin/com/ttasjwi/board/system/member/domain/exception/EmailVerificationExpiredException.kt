package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import java.time.ZonedDateTime

class EmailVerificationExpiredException(
    email: String,
    expiredAt: ZonedDateTime,
    currentTime: ZonedDateTime
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.EmailVerificationExpired",
    source = "emailVerification",
    args = listOf(email, expiredAt, currentTime),
    debugMessage = "이메일 인증이 만료됐습니다. 다시 인증해주세요. (email=$email, expiredAt=$expiredAt, currentTime=$currentTime)"
)
