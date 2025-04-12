package com.ttasjwi.board.system.member.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.TokenRefreshRequest
import com.ttasjwi.board.system.member.domain.TokenRefreshResponse
import com.ttasjwi.board.system.member.domain.TokenRefreshUseCase

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
