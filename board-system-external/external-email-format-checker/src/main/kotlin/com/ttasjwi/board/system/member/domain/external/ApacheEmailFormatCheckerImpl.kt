package com.ttasjwi.board.system.member.domain.external

import com.ttasjwi.board.system.core.annotation.component.AppComponent
import org.apache.commons.validator.routines.EmailValidator

@AppComponent
internal class ApacheEmailFormatCheckerImpl : ExternalEmailFormatChecker {

    private val apacheEmailValidator = EmailValidator.getInstance()
    
    override fun isValidFormatEmail(value: String): Boolean {
        return apacheEmailValidator.isValid(value)
    }
}
