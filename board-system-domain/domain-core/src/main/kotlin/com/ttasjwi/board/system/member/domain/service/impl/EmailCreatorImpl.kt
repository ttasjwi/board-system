package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.external.ExternalEmailFormatChecker
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.service.EmailCreator

@DomainService
internal class EmailCreatorImpl(
    private val externalEmailFormatChecker: ExternalEmailFormatChecker,
) : EmailCreator {

    override fun create(value: String): Result<Email> {
        return kotlin.runCatching {
            Email.create(value) { externalEmailFormatChecker.isValidFormatEmail(it) }
        }
    }
}
