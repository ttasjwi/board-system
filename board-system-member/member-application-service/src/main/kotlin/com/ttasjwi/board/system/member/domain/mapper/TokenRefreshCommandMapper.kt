package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.member.domain.TokenRefreshRequest
import com.ttasjwi.board.system.member.domain.dto.TokenRefreshCommand

@ApplicationCommandMapper
internal class TokenRefreshCommandMapper(
    private val timeManager: TimeManager
) {

    fun mapToCommand(request: TokenRefreshRequest): TokenRefreshCommand {
        if (request.refreshToken == null) {
            throw NullArgumentException("refreshToken")
        }
        return TokenRefreshCommand(request.refreshToken!!, timeManager.now())
    }
}
