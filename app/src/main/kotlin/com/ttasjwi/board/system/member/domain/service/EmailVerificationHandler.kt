package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime

interface EmailVerificationHandler {

    /**
     * code 를 통해 유효한 이메일 인증인 지 확인하고 유효하다면 이메일을 '인증처리'합니다.
     */
    fun codeVerify(
        emailVerification: EmailVerification,
        code: String,
        currentTime: ZonedDateTime
    ): EmailVerification

    /**
     * 이메일 인증이 현재 검증된 상태이면서 유효한지 확인합니다.
     */
    fun checkVerifiedAndCurrentlyValid(
        emailVerification: EmailVerification,
        currentTime: ZonedDateTime
    )
}
