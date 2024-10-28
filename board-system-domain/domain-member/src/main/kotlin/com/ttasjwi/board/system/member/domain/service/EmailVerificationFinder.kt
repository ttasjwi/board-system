package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationFinder {
    fun findByEmailOrNull(email: Email): EmailVerification?
}
