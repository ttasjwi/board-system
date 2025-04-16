package com.ttasjwi.board.system.user.domain

import java.time.ZonedDateTime

interface SocialLoginUseCase {

    /**
     * 소셜 연동 정보를 기반으로 액세스토큰, 리프레시 토큰을 얻어옵니다.
     * 만약 소셜 연동에 해당하는 회원이 없으면 회원을 생성합니다.
     */
    fun socialLogin(request: SocialLoginRequest): SocialLoginResponse
}

data class SocialLoginRequest(
    val socialServiceName: String,
    val socialServiceUserId: String,
    val email: String,
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
