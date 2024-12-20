package com.ttasjwi.board.system.auth.application.mapper

import com.ttasjwi.board.system.auth.application.dto.TokenRefreshCommand
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.core.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.time.TimeManager

@ApplicationCommandMapper
internal class TokenRefreshCommandMapper(
    private val timeManager: TimeManager
) {

    fun mapToCommand(request: TokenRefreshRequest): TokenRefreshCommand {
        if (request.refreshToken == null) {
            throw NullArgumentException("refreshToken")
        }
        return TokenRefreshCommand(request.refreshToken, timeManager.now())
    }
}
