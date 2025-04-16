package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.annotation.component.UseCase
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.user.domain.mapper.SocialLoginCommandMapper
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.processor.SocialLoginProcessor

@UseCase
internal class SocialLoginUseCaseImpl(
    private val commandMapper: SocialLoginCommandMapper,
    private val processor: SocialLoginProcessor,
) : SocialLoginUseCase {

    override fun socialLogin(request: SocialLoginRequest): SocialLoginResponse {
        val command = commandMapper.mapToCommand(request)
        val (createdMember, accessToken, refreshToken) = processor.socialLogin(command)
        return makeResponse(createdMember, accessToken, refreshToken)
    }

    /**
     * 소셜 로그인 결과를 생성합니다.
     */
    private fun makeResponse(
        createdMember: Member?,
        accessToken: AccessToken,
        refreshToken: RefreshToken,
    ): SocialLoginResponse {
        return SocialLoginResponse(
            accessToken = accessToken.tokenValue,
            accessTokenType = "Bearer",
            accessTokenExpiresAt = accessToken.expiresAt.toZonedDateTime(),
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt.toZonedDateTime(),
            memberCreated = createdMember != null,
            createdMember = createdMember?.let {
                SocialLoginResponse.CreatedMember(
                    memberId = it.memberId.toString(),
                    email = it.email,
                    username = it.username,
                    nickname = it.nickname,
                    role = it.role.name,
                    registeredAt = it.registeredAt.toZonedDateTime()
                )
            }
        )
    }
}
