package com.ttasjwi.board.system.user.domain

import java.time.ZonedDateTime

interface SocialLoginUseCase {

    fun socialLogin(request: SocialLoginRequest): SocialLoginResponse
}

data class SocialLoginRequest(
    val code: String?,
    val state: String?,
)

data class SocialLoginResponse(
    val accessToken: String,
    val accessTokenType: String = "Bearer",
    val accessTokenExpiresAt: ZonedDateTime,
    val refreshToken: String,
    val refreshTokenExpiresAt: ZonedDateTime,
    val userCreated: Boolean,
    val createdUser: CreatedUser?,
) {

    data class CreatedUser(
        val userId: String,
        val email: String,
        val username: String,
        val nickname: String,
        val role: String,
        val registeredAt: ZonedDateTime,
    )
}
