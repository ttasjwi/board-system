package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.service.EmailVerificationCreator
import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.global.time.AppDateTime

@DomainService
internal class EmailVerificationCreatorImpl : EmailVerificationCreator {

    override fun create(email: String, currentTime: AppDateTime): EmailVerification {
        return EmailVerification.create(email, currentTime)
    }
}

