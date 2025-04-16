package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.user.domain.mapper.LoginCommandMapper
import com.ttasjwi.board.system.user.domain.processor.LoginProcessor

@UseCase
internal class LoginUseCaseImpl(
    private val commandMapper: LoginCommandMapper,
    private val processor: LoginProcessor,
) : LoginUseCase {

    override fun login(request: LoginRequest): LoginResponse {
        val command = commandMapper.mapToCommand(request)

        val (accessToken, refreshToken) = processor.login(command)

        return makeResponse(accessToken, refreshToken)
    }

    private fun makeResponse(accessToken: AccessToken, refreshToken: RefreshToken): LoginResponse {
        return LoginResponse(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt.toZonedDateTime(),
            accessTokenType = "Bearer",
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt.toZonedDateTime(),
        )
    }
}
