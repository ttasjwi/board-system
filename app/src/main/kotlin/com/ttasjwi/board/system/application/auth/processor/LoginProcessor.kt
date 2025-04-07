package com.ttasjwi.board.system.application.auth.processor

import com.ttasjwi.board.system.application.auth.dto.LoginCommand
import com.ttasjwi.board.system.application.auth.exception.LoginFailureException
import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.*
import com.ttasjwi.board.system.global.annotation.ApplicationProcessor
import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class LoginProcessor(
    private val memberFinder: MemberFinder,
    private val passwordManager: PasswordManager,
    private val authMemberCreator: AuthMemberCreator,
    private val accessTokenManager: AccessTokenManager,
    private val refreshTokenManager: RefreshTokenManager,
    private val refreshTokenHolderFinder: RefreshTokenHolderFinder,
    private val refreshTokenHolderManager: RefreshTokenHolderManager,
    private val refreshTokenHolderAppender: RefreshTokenHolderAppender,
    private val authEventCreator: AuthEventCreator
) {

    companion object {
        private val log = getLogger(LoginProcessor::class.java)
    }

    @Transactional
    fun login(command: LoginCommand): LoggedInEvent {
        log.info { "로그인 처리를 시작합니다. (email=${command.email})" }
        // 회원 조회
        val member = findMember(command)

        // 패스워드 매칭
        matchesPassword(command.rawPassword, member.password)

        // 인증 성공
        val authMember = authMemberCreator.create(member)

        // 토큰 발급
        val (accessToken, refreshToken) = createTokens(authMember, command.currentTime)

        // 리프레시 토큰 홀더 업데이트
        upsertRefreshTokenHolder(authMember, refreshToken, command.currentTime)

        val loggedInEvent = authEventCreator.onLoginSuccess(accessToken, refreshToken)

        log.info { "로그인 처리를 성공했습니다. (email=${command.email})" }

        return loggedInEvent
    }

    /**
     * 로그인을 할 회원 조회
     */
    private fun findMember(command: LoginCommand): Member {
        log.info { "로그인 처리 - 회원을 조회합니다. (email=${command.email})" }
        val member = memberFinder.findByEmailOrNull(command.email)

        if (member == null) {
            val ex = LoginFailureException("로그인 실패 - 일치하는 이메일(email=${command.email})의 회원을 찾지 못 함")
            log.warn(ex)
            throw ex
        }
        log.info { "로그인 처리 - 회원이 조회됐습니다. (memberId=${member.id},email=${command.email})" }
        return member
    }

    /**
     * 패스워드 비교
     */
    private fun matchesPassword(
        rawPassword: String,
        encodedPassword: String,
    ) {
        log.info { "로그인 처리 - 패스워드 일치 여부를 확인합니다." }

        if (!passwordManager.matches(rawPassword, encodedPassword)) {
            val ex = LoginFailureException("로그인 처리 실패 - 패스워드 불일치")
            log.warn(ex)
            throw ex
        }
        log.info { "패스워드가 일치합니다." }
    }

    /**
     * 액세스 토큰, 리프레시 토큰 생성
     */
    private fun createTokens(authMember: AuthMember, currentTime: AppDateTime): Pair<AccessToken, RefreshToken> {
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
        currentTime: AppDateTime
    ) {
        val refreshTokenHolder = refreshTokenHolderFinder.findByMemberIdOrNull(authMember.memberId)
            ?: refreshTokenHolderManager.createRefreshTokenHolder(authMember)

        val addedRefreshTokenHolder = refreshTokenHolderManager.addNewRefreshToken(refreshTokenHolder, refreshToken)

        refreshTokenHolderAppender.append(authMember.memberId, addedRefreshTokenHolder, currentTime)
    }
}
