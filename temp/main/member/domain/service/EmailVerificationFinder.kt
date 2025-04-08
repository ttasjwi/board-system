package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.EmailVerification

interface EmailVerificationFinder {
    fun findByEmailOrNull(email: String): EmailVerification?
}
