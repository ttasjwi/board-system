package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResult
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.core.time.fixture.timeFixture

class SocialLoginUseCaseFixture : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResult {
        return SocialLoginResult(
            accessToken = "accessToken",
            accessTokenExpiresAt = timeFixture(minute = 30),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = timeFixture(dayOfMonth = 2, minute = 0),
            memberCreated = true,
            createdMember = SocialLoginResult.CreatedMember(
                memberId = 1L,
                email = request.email,
                username = "createdusername",
                nickname = "creatednickname",
                role = "USER",
                registeredAt = timeFixture(minute = 0)
            )
        )
    }
}
