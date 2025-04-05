package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshRequest
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshResponse
import com.ttasjwi.board.system.auth.application.usecase.TokenRefreshUseCase
import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

class TokenRefreshUseCaseFixture : TokenRefreshUseCase {

    override fun tokenRefresh(request: TokenRefreshRequest): TokenRefreshResponse {
        return TokenRefreshResponse(
            accessToken = "newAccessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = appDateTimeFixture(minute = 30).toZonedDateTime(),
            refreshToken = request.refreshToken!!,
            refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 2).toZonedDateTime(),
            refreshTokenRefreshed = false,
        )
    }
}
