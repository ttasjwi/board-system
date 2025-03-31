package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResponse
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class LoginUseCaseFixture : LoginUseCase {

    override fun login(request: LoginRequest): LoginResponse {
        return LoginResponse(
            accessToken = "accessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = timeFixture(minute = 30),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = timeFixture(dayOfMonth = 2),
        )
    }
}
