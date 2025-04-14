package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.member.domain.UsernameAvailableRequest
import com.ttasjwi.board.system.member.domain.dto.UsernameAvailableQuery

@ApplicationQueryMapper
internal class UsernameAvailableQueryMapper(
    private val localeResolver: LocaleResolver,
) {

    fun mapToQuery(request: UsernameAvailableRequest): UsernameAvailableQuery {
        if (request.username == null) {
            throw NullArgumentException("username")
        }
        return UsernameAvailableQuery(
            username = request.username!!,
            locale = localeResolver.getCurrentLocale()
        )
    }
}
