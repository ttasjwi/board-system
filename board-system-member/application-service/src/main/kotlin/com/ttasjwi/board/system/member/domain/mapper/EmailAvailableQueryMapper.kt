package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.member.domain.EmailAvailableRequest
import com.ttasjwi.board.system.member.domain.dto.EmailAvailableQuery

@ApplicationQueryMapper
internal class EmailAvailableQueryMapper(
    private val localeResolver: LocaleResolver,
) {

    fun mapToQuery(request: EmailAvailableRequest): EmailAvailableQuery {
        if (request.email == null) {
            throw NullArgumentException("email")
        }
        return EmailAvailableQuery(
            email = request.email!!,
            locale = localeResolver.getCurrentLocale()
        )
    }
}
