package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.service.EmailVerificationCreator
import java.time.ZonedDateTime

@DomainService
internal class EmailverificationCreatorImpl : EmailVerificationCreator {

    override fun create(email: Email, currentTime: ZonedDateTime): EmailVerification {
        return EmailVerification.create(email, currentTime)
    }
}
