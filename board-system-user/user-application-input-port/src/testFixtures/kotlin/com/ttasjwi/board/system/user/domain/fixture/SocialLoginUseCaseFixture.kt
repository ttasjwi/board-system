package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.SocialLoginRequest
import com.ttasjwi.board.system.user.domain.SocialLoginResponse
import com.ttasjwi.board.system.user.domain.SocialLoginUseCase

class SocialLoginUseCaseFixture : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        return SocialLoginResponse(
            accessToken = "accessToken",
            accessTokenType = "Bearer",
            accessTokenExpiresAt = appDateTimeFixture(minute = 30).toZonedDateTime(),
            refreshToken = "refreshToken",
            refreshTokenExpiresAt = appDateTimeFixture(dayOfMonth = 2, minute = 0).toZonedDateTime(),
            userCreated = true,
            createdUser = SocialLoginResponse.CreatedUser(
                userId = "1",
                email = request.email,
                username = "createdusername",
                nickname = "creatednickname",
                role = "USER",
                registeredAt = appDateTimeFixture(minute = 0).toZonedDateTime()
            )
        )
    }
}
