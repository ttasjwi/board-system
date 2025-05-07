package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.exception.OAuth2AuthorizationRequestNotFoundException
import com.ttasjwi.board.system.user.domain.model.*
import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort
import com.ttasjwi.board.system.user.domain.port.OAuth2ClientRegistrationPersistencePort
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort
import com.ttasjwi.board.system.user.domain.port.UserPersistencePort
import com.ttasjwi.board.system.user.domain.service.OAuth2UserAuthenticator
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.service.UserCreator

@ApplicationProcessor
class SocialLoginProcessor(
    private val oAuth2AuthorizationRequestPersistencePort: OAuth2AuthorizationRequestPersistencePort,
    private val oAuth2ClientRegistrationPersistencePort: OAuth2ClientRegistrationPersistencePort,
    private val oAuth2UserAuthenticator: OAuth2UserAuthenticator,
    private val userPersistencePort: UserPersistencePort,
    private val socialConnectionPersistencePort: SocialConnectionPersistencePort,
    private val userCreator: UserCreator,
    private val accessTokenGeneratePort: AccessTokenGeneratePort,
    private val refreshTokenHandler: RefreshTokenHandler,
) {

    private val socialConnectionIdGenerator: IdGenerator = IdGenerator.create()

    fun socialLogin(command: SocialLoginCommand): Triple<User?, AccessToken, RefreshToken> {
        // state 를 통해 인가 요청 조회, 삭제
        val authorizationRequest = removeAuthorizationRequestOrThrow(command.state)

        // ClientRegistration 가져오기
        val clientRegistration = oAuth2ClientRegistrationPersistencePort.findById(authorizationRequest.oAuth2ClientRegistrationId)!!

        // 소셜서비스 인가
        val oAuth2UserPrincipal = authenticateOAuth2User(command.code, clientRegistration, authorizationRequest)

        // 소셜서비스 사용자 정보를 통해 우리 서비스의 사용자 식별
        val (userCreated, user) = getUserOrCreate(oAuth2UserPrincipal, command.currentTime)

        // 인증회원 구성
        val authUser = AuthUser.create(user.userId, user.role)

        // 토큰 발급
        val accessToken = accessTokenGeneratePort.generate(authUser, command.currentTime, command.currentTime.plusMinutes(30))
        val refreshToken = refreshTokenHandler.createAndPersist(authUser.userId, command.currentTime)

        return Triple(
            if (userCreated) user else null,
            accessToken,
            refreshToken
        )
    }

    private fun removeAuthorizationRequestOrThrow(state: String): OAuth2AuthorizationRequest {
        return oAuth2AuthorizationRequestPersistencePort.remove(state)
            ?: throw OAuth2AuthorizationRequestNotFoundException(state)
    }

    private fun authenticateOAuth2User(code: String, clientRegistration: OAuth2ClientRegistration, authorizationRequest: OAuth2AuthorizationRequest): OAuth2UserPrincipal {
        return oAuth2UserAuthenticator.authenticate(code, clientRegistration, authorizationRequest)
    }

    /**
     * 소셜 연동된 회원을 얻어옵니다.
     * 소셜 연동이 없을 경우, 이메일에 해당하는 회원을 얻어옵니다.
     * 소셜 연동도 없고, 이메일에 해당하는 회원이 없을 경우 회원을 생성합니다.
     */
    private fun getUserOrCreate(oAuth2UserPrincipal: OAuth2UserPrincipal, currentTime: AppDateTime): Pair<Boolean, User> {
        // 소셜 연동에 해당하는 회원을 식별하는데 성공하면, 회원을 그대로 반환
        val socialService = SocialService.restore(oAuth2UserPrincipal.socialServiceName)
        val socialServiceUserId = oAuth2UserPrincipal.socialServiceUserId
        val email = oAuth2UserPrincipal.email

        val socialConnection = socialConnectionPersistencePort.read(socialService, oAuth2UserPrincipal.socialServiceUserId)
        if (socialConnection != null) {
            return Pair(false, userPersistencePort.findByIdOrNull(socialConnection.userId)!!)
        }

        // 소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 시키고 회원을 그대로 반환
        val user = userPersistencePort.findByEmailOrNull(email)
        if (user != null) {
            createSocialConnectionAndSave(user.userId, socialService, socialServiceUserId, currentTime)
            return Pair(false, user)
        }
        // 회원도 없고, 소셜 연동도 찾지 못 했으면 회원 생성 및 소셜 연동 생성 후 회원 반환
        return createNewUserAndPersist(socialService, socialServiceUserId, email, currentTime)
    }


    /**
     * 신규회원을 생성합니다.
     */
    private fun createNewUserAndPersist(
        socialService: SocialService,
        socialServiceUserId: String,
        email: String,
        currentTime: AppDateTime
    ): Pair<Boolean, User> {
        // 회원 생성
        val user = userCreator.createRandom(email, currentTime)

        // 회원 저장
        userPersistencePort.save(user)

        // 소셜연동 생성 및 저장
        createSocialConnectionAndSave(user.userId, socialService, socialServiceUserId, currentTime)
        return Pair(true, user)
    }

    /**
     * 소셜 연동을 생성하고, 저장합니다.
     */
    private fun createSocialConnectionAndSave(
        userId: Long,
        socialService: SocialService,
        socialServiceUserId: String,
        currentTime: AppDateTime
    ) {
        val socialConnection = SocialConnection.create(
            socialConnectionId = socialConnectionIdGenerator.nextId(),
            userId = userId,
            socialService = socialService,
            socialServiceUserId = socialServiceUserId,
            currentTime = currentTime,
        )
        socialConnectionPersistencePort.save(socialConnection)
    }
}
