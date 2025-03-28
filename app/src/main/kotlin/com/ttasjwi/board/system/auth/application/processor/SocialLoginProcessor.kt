package com.ttasjwi.board.system.auth.application.processor

import com.ttasjwi.board.system.auth.application.dto.SocialLoginCommand
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResult
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.*
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.MemberId
import com.ttasjwi.board.system.member.domain.service.*
import java.time.ZonedDateTime

@ApplicationProcessor
internal class SocialLoginProcessor(
    private val memberFinder: MemberFinder,
    private val socialConnectionCreator: SocialConnectionCreator,
    private val socialConnectionStorage: SocialConnectionStorage,
    private val passwordManager: PasswordManager,
    private val usernameCreator: UsernameCreator,
    private val nicknameCreator: NicknameCreator,
    private val memberCreator: MemberCreator,
    private val memberAppender: MemberAppender,
    private val authMemberCreator: AuthMemberCreator,
    private val accessTokenManager: AccessTokenManager,
    private val refreshTokenManager: RefreshTokenManager,
    private val refreshTokenHolderFinder: RefreshTokenHolderFinder,
    private val refreshTokenHolderManager: RefreshTokenHolderManager,
    private val refreshTokenHolderAppender: RefreshTokenHolderAppender,
) {

    fun socialLogin(command: SocialLoginCommand): SocialLoginResult {
        // 회원 획득 (없다면 생성)
        val (memberCreated, member) = getMemberOrCreate(command)

        // 인증회원 구성
        val authMember = authMemberCreator.create(member)

        // 토큰 발급
        val (accessToken, refreshToken) = createTokens(authMember, command.currentTime)

        // 리프레시 토큰 홀더 업데이트
        upsertRefreshTokenHolder(authMember, refreshToken, command.currentTime)

        // 소셜 로그인 결과 생성
        return makeResult(accessToken, refreshToken, memberCreated, member)
    }

    /**
     * 소셜 연동된 회원을 얻어옵니다.
     * 소셜 연동이 없을 경우, 이메일에 해당하는 회원을 얻어옵니다.
     * 소셜 연동도 없고, 이메일에 해당하는 회원이 없을 경우 회원을 생성합니다.
     */
    private fun getMemberOrCreate(command: SocialLoginCommand): Pair<Boolean, Member> {
        // 소셜 연동에 해당하는 회원을 식별하는데 성공하면, 회원을 그대로 반환
        val socialConnection = socialConnectionStorage.findBySocialServiceUserOrNull(command.socialServiceUser)
        if (socialConnection != null) {
            return Pair(false, memberFinder.findByIdOrNull(socialConnection.memberId)!!)
        }

        // 소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 시키고 회원을 그대로 반환
        val member = memberFinder.findByEmailOrNull(command.email)
        if (member != null) {
            createSocialConnectionAndSave(member.id!!, command)
            return Pair(false, member)
        }

        // 회원도 없고, 소셜 연동도 찾지 못 했으면 회원 생성 및 소셜 연동 생성 후 회원 반환
        return createNewMember(command)
    }

    /**
     * 신규회원을 생성합니다.
     */
    private fun createNewMember(
        command: SocialLoginCommand
    ): Pair<Boolean, Member> {
        // 회원 생성
        val member = memberCreator.create(
            email = command.email,
            password = passwordManager.createRandomRawPassword(),
            username = usernameCreator.createRandom(),
            nickname = nicknameCreator.createRandom(),
            currentTime = command.currentTime,
        )
        // 회원 저장
        val savedMember = memberAppender.save(member)
        createSocialConnectionAndSave(savedMember.id!!, command)
        return Pair(true, member)
    }

    /**
     * 소셜 연동을 생성하고, 저장합니다.
     */
    private fun createSocialConnectionAndSave(
        memberId: MemberId,
        command: SocialLoginCommand
    ) {
        val socialConnection = socialConnectionCreator.create(
            memberId,
            command.socialServiceUser,
            command.currentTime
        )
        socialConnectionStorage.save(socialConnection)
    }

    /**
     * 액세스 토큰, 리프레시 토큰 생성
     */
    private fun createTokens(authMember: AuthMember, currentTime: ZonedDateTime): Pair<AccessToken, RefreshToken> {
        val accessToken = accessTokenManager.generate(authMember, currentTime)
        val refreshToken = refreshTokenManager.generate(authMember.memberId, currentTime)
        return Pair(accessToken, refreshToken)
    }

    /**
     * 리프레시 토큰 홀더 조회 또는 생성 -> 리프레시 토큰 홀더 업데이트 -> 리프레시 토큰 저장
     */
    private fun upsertRefreshTokenHolder(
        authMember: AuthMember,
        refreshToken: RefreshToken,
        currentTime: ZonedDateTime
    ) {
        val refreshTokenHolder = refreshTokenHolderFinder.findByMemberIdOrNull(authMember.memberId)
            ?: refreshTokenHolderManager.createRefreshTokenHolder(authMember)

        val addedRefreshTokenHolder = refreshTokenHolderManager.addNewRefreshToken(refreshTokenHolder, refreshToken)

        refreshTokenHolderAppender.append(authMember.memberId, addedRefreshTokenHolder, currentTime)
    }

    /**
     * 소셜 로그인 결과를 생성합니다.
     */
    private fun makeResult(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        memberCreated: Boolean,
        member: Member
    ): SocialLoginResult {
        return SocialLoginResult(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt,
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt,
            memberCreated = memberCreated,
            createdMember = if (memberCreated) {
                SocialLoginResult.CreatedMember(
                    memberId = member.id!!.value,
                    email = member.email.value,
                    username = member.username.value,
                    nickname = member.nickname.value,
                    role = member.role.name,
                    registeredAt = member.registeredAt
                )
            } else {
                null
            }
        )
    }
}
