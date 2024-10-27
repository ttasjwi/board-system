package com.ttasjwi.board.system.email.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.email.domain.model.EmailVerification
import com.ttasjwi.board.system.email.domain.service.EmailVerificationCreator
import java.time.ZonedDateTime

@DomainService
internal class EmailverificationCreatorImpl : EmailVerificationCreator {

    override fun create(email: Email, currentTime: ZonedDateTime): EmailVerification {
        TODO("Not yet implemented")
    }
}
