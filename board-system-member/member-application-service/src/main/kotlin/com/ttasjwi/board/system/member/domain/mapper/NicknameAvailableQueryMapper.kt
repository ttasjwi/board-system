package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.member.domain.NicknameAvailableRequest
import com.ttasjwi.board.system.member.domain.dto.NicknameAvailableQuery

@ApplicationQueryMapper
internal class NicknameAvailableQueryMapper(
    private val localeResolver: LocaleResolver
) {

    companion object {
        private val log = getLogger(NicknameAvailableQueryMapper::class.java)
    }

    fun mapToQuery(request: NicknameAvailableRequest): NicknameAvailableQuery {
        if (request.nickname == null) {
            throw NullArgumentException("nickname")
        }
        return NicknameAvailableQuery(
            nickname = request.nickname!!,
            locale = localeResolver.getCurrentLocale(),
        )
    }
}
