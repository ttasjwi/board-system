package com.ttasjwi.board.system.auth.application.usecase.fixture

import com.ttasjwi.board.system.auth.application.usecase.SocialLoginRequest
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResponse
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginUseCase
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

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
