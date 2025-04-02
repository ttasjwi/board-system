package com.ttasjwi.board.system.member.domain.external.impl

import com.ttasjwi.board.system.member.domain.external.ExternalEmailFormatChecker
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.stereotype.Component

@Component
internal class ApacheEmailFormatCheckerImpl : ExternalEmailFormatChecker {

    private val apacheEmailValidator = EmailValidator.getInstance()

    override fun isValidFormatEmail(value: String): Boolean {
        return apacheEmailValidator.isValid(value)
    }
}
