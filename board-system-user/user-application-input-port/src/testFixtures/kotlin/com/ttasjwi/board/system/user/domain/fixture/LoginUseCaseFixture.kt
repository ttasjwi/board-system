package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.LoginRequest
import com.ttasjwi.board.system.user.domain.LoginResponse
import com.ttasjwi.board.system.user.domain.LoginUseCase

class LoginUseCaseFixture : LoginUseCase {

    override fun login(request: LoginRequest): LoginResponse {
        return LoginResponse(
            accessToken = "accessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = appDateTimeFixture(minute = 30).toZonedDateTime(),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 2).toZonedDateTime(),
        )
    }
}
