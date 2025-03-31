package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResponse
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.common.time.fixture.timeFixture

class SocialLoginUseCaseFixture : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        return SocialLoginResponse(
            accessToken = "accessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = timeFixture(minute = 30),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = timeFixture(dayOfMonth = 2, minute = 0),
            memberCreated = true,
            createdMember = SocialLoginResponse.CreatedMember(
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
