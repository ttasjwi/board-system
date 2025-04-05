package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResponse
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
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
