package com.ttasjwi.board.system.member.domain.policy.impl

import com.ttasjwi.board.system.common.annotation.component.DomainPolicy
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailFormatException
import com.ttasjwi.board.system.member.domain.policy.EmailFormatPolicy
import org.apache.commons.validator.routines.EmailValidator

@DomainPolicy
class EmailFormatPolicyImpl : EmailFormatPolicy {

    private val apacheEmailValidator = EmailValidator.getInstance()

    override fun validate(email: String): Result<String> = runCatching {
        if (apacheEmailValidator.isValid(email)) {
            return Result.success(email)
        }
        return Result.failure(InvalidEmailFormatException(email))
    }
}
