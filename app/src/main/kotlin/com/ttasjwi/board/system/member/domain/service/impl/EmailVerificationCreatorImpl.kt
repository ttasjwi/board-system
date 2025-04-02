package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationCreator

@DomainService
internal class EmailVerificationCreatorImpl : EmailVerificationCreator {

    override fun create(email: String, currentTime: AppDateTime): EmailVerification {
        return EmailVerification.create(email, currentTime)
    }
}

