package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.domain.member.exception.InvalidEmailFormatException
import com.ttasjwi.board.system.domain.member.service.EmailManager
import org.apache.commons.validator.routines.EmailValidator

@DomainService
class EmailManagerImpl : EmailManager {

    private val apacheEmailValidator = EmailValidator.getInstance()

    override fun validate(email: String): Result<String> = runCatching {
        if (apacheEmailValidator.isValid(email)) {
            return Result.success(email)
        }
        return Result.failure(InvalidEmailFormatException(email))
    }
}
