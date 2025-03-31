package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResult
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class LoginUseCaseFixture : LoginUseCase {

    override fun login(request: LoginRequest): LoginResult {
        return LoginResult(
            accessToken = "accessToken",
            accessTokenExpiresAt = timeFixture(minute = 30),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = timeFixture(dayOfMonth = 2),
        )
    }
}
