package com.ttasjwi.board.system.application.auth.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

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
