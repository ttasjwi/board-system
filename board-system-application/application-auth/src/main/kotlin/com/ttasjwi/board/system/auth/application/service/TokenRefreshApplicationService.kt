package com.ttasjwi.board.system.auth.application.service

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResult
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import com.ttasjwi.board.system.core.annotation.component.ApplicationService

@ApplicationService
internal class TokenRefreshApplicationService : TokenRefreshUseCase {
    override fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResult {
        TODO("Not yet implemented")
    }
}
