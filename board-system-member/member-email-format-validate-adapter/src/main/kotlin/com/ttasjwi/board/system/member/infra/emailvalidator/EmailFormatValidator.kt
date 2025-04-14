package com.ttasjwi.board.system.member.infra.emailvalidator

import com.ttasjwi.board.system.common.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.port.EmailFormatValidatePort
import org.apache.commons.validator.routines.EmailValidator

@AppComponent
class EmailFormatValidator : EmailFormatValidatePort {

    private val delegate: EmailValidator = EmailValidator.getInstance()

    override fun validate(email: String): Result<String> = kotlin.runCatching {
        if (delegate.isValid(email)) {
            return Result.success(email)
        }
        return Result.failure(InvalidEmailFormatException(email))
    }
}
