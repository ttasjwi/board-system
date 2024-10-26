package com.ttasjwi.board.system.email.domain.service.impl

import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.email.domain.model.Email
import com.ttasjwi.board.system.email.domain.service.EmailCreator

@DomainService
internal class EmailCreatorImpl : EmailCreator {

    override fun create(value: String): Result<Email> {
        TODO("Not yet implemented")
    }
}
