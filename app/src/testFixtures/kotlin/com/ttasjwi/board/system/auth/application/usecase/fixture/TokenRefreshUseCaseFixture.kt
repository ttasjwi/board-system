package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResponse
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class TokenRefreshUseCaseFixture : TokenRefreshUseCase {

    override fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse {
        return TokenRefreshResponse(
            accessToken = "newAccessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = timeFixture(minute = 30),
            refreshToken = request.refreshToken!!,
            refreshTokenExpiresAt = timeFixture(dayOfMonth = 2),
            refreshTokenRefreshed = false,
        )
    }
}
