package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.member.domain.dto.LoginCommand
import com.ttasjwi.board.system.member.domain.exception.LoginFailureException
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.member.domain.port.PasswordEncryptionPort
import com.ttasjwi.board.system.member.domain.service.RefreshTokenHandler


@ApplicationProcessor
internal class LoginProcessor(
    private val memberPersistencePort: MemberPersistencePort,
    private val passwordEncryptionPort: PasswordEncryptionPort,
    private val accessTokenGeneratePort: AccessTokenGeneratePort,
    private val refreshTokenHandler: RefreshTokenHandler,
) {

    fun login(command: LoginCommand): Pair<AccessToken, RefreshToken> {
        val member = getMemberOrThrow(command)
        matchesPassword(command.rawPassword, member.password)

        val authMember = AuthMember.create(member.memberId, member.role)

        val accessToken =
            accessTokenGeneratePort.generate(authMember, command.currentTime, command.currentTime.plusMinutes(30))
        val refreshToken = refreshTokenHandler.createAndPersist(authMember.memberId, command.currentTime)

        return Pair(accessToken, refreshToken)
    }

    /**
     * 로그인을 할 회원 조회
     */
    private fun getMemberOrThrow(command: LoginCommand): Member {
        return memberPersistencePort.findByEmailOrNull(command.email)
            ?: throw LoginFailureException("로그인 실패 - 일치하는 이메일(email=${command.email})의 회원을 찾지 못 함")
    }

    /**
     * 패스워드 비교
     */
    private fun matchesPassword(rawPassword: String, encodedPassword: String) {
        if (!passwordEncryptionPort.matches(rawPassword, encodedPassword)) {
            throw LoginFailureException("로그인 처리 실패 - 패스워드 불일치")
        }
    }
}
