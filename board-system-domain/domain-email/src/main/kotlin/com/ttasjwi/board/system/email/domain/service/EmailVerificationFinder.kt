package com.ttasjwi.board.system.email.domain.service

import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.email.domain.model.EmailVerification

interface EmailVerificationFinder {

    fun findByEmailOrNull(email: Email): EmailVerification?
}
