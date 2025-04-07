package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.EmailVerification

interface EmailVerificationFinder {
    fun findByEmailOrNull(email: String): EmailVerification?
}
