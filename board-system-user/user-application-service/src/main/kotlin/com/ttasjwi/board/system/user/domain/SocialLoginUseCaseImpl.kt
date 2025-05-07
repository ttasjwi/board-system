package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.user.domain.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.processor.SocialLoginProcessor

@UseCase
internal class SocialLoginUseCaseImpl(
    private val commandMapper: SocialLoginCommandMapper,
    private val processor: SocialLoginProcessor,
) : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        val command = commandMapper.mapToCommand(request)
        val (createdUser, accessToken, refreshToken) = processor.socialLogin(command)

        return makeResponse(accessToken, refreshToken, createdUser)
    }

    private fun makeResponse(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        createdUser: User?
    ) = SocialLoginResponse(
        accessToken = accessToken.tokenValue,
        accessTokenExpiresAt = accessToken.expiresAt.toZonedDateTime(),
        accessTokenType = "Bearer",
        refreshToken = refreshToken.tokenValue,
        refreshTokenExpiresAt = refreshToken.expiresAt.toZonedDateTime(),
        createdUser = createdUser?.let {
            SocialLoginResponse.CreatedUser(
                userId = it.userId.toString(),
                email = it.email,
                username = it.username,
                nickname = it.nickname,
                role = it.role.name,
                registeredAt = it.registeredAt.toZonedDateTime(),
            )
        },
        userCreated = createdUser != null,
    )
}
