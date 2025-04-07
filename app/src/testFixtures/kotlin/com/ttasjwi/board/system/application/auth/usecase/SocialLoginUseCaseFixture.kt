package com.ttasjwi.board.system.application.auth.usecase

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture

class SocialLoginUseCaseFixture : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        return SocialLoginResponse(
            accessToken = "accessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = appDateTimeFixture(minute = 30).toZonedDateTime(),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 0).toZonedDateTime(),
            memberCreated = true,
            createdMember = SocialLoginResponse.CreatedMember(
                memberId = "1",
                email = request.email,
                username = "createdusername",
                nickname = "creatednickname",
                role = "USER",
                registeredAt = appDateTimeFixture(minute = 0).toZonedDateTime()
            )
        )
    }
}
