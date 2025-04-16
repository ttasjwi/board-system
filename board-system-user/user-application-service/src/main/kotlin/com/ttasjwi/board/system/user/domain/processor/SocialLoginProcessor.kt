package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.AccessToken
import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.model.SocialConnection
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import com.ttasjwi.board.system.user.domain.port.SocialConnectionPersistencePort
import com.ttasjwi.board.system.user.domain.service.MemberCreator
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class SocialLoginProcessor(
    private val memberPersistencePort: MemberPersistencePort,
    private val memberCreator: MemberCreator,
    private val socialConnectionPersistencePort: SocialConnectionPersistencePort,
    private val accessTokenGeneratePort: AccessTokenGeneratePort,
    private val refreshTokenHandler: RefreshTokenHandler,
) {

    private val socialConnectionIdGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun socialLogin(command: SocialLoginCommand): Triple<Member?, AccessToken, RefreshToken> {
        // 회원 획득 (없다면 생성)
        val (memberCreated, member) = getMemberOrCreate(command)

        // 인증회원 구성
        val authMember = AuthMember.create(member.memberId, member.role)

        // 토큰 발급
        val accessToken =
            accessTokenGeneratePort.generate(authMember, command.currentTime, command.currentTime.plusMinutes(30))
        val refreshToken = refreshTokenHandler.createAndPersist(authMember.memberId, command.currentTime)
        return Triple(
            if (memberCreated) member else null,
            accessToken,
            refreshToken
        )
    }

    /**
     * 소셜 연동된 회원을 얻어옵니다.
     * 소셜 연동이 없을 경우, 이메일에 해당하는 회원을 얻어옵니다.
     * 소셜 연동도 없고, 이메일에 해당하는 회원이 없을 경우 회원을 생성합니다.
     */
    private fun getMemberOrCreate(command: SocialLoginCommand): Pair<Boolean, Member> {
        // 소셜 연동에 해당하는 회원을 식별하는데 성공하면, 회원을 그대로 반환
        val socialConnection = socialConnectionPersistencePort.findBySocialServiceUserOrNull(command.socialServiceUser)
        if (socialConnection != null) {
            return Pair(false, memberPersistencePort.findByIdOrNull(socialConnection.memberId)!!)
        }

        // 소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 시키고 회원을 그대로 반환
        val member = memberPersistencePort.findByEmailOrNull(command.email)
        if (member != null) {
            createSocialConnectionAndSave(member.memberId, command)
            return Pair(false, member)
        }
        // 회원도 없고, 소셜 연동도 찾지 못 했으면 회원 생성 및 소셜 연동 생성 후 회원 반환
        return createNewMemberAndPersist(command)
    }

    /**
     * 신규회원을 생성합니다.
     */
    private fun createNewMemberAndPersist(
        command: SocialLoginCommand
    ): Pair<Boolean, Member> {
        // 회원 생성
        val member = memberCreator.createRandom(command.email, command.currentTime)
        // 회원 저장
        val savedMember = memberPersistencePort.save(member)
        createSocialConnectionAndSave(savedMember.memberId, command)
        return Pair(true, member)
    }

    /**
     * 소셜 연동을 생성하고, 저장합니다.
     */
    private fun createSocialConnectionAndSave(
        memberId: Long,
        command: SocialLoginCommand
    ) {
        val socialConnection = SocialConnection.create(
            socialConnectionId = socialConnectionIdGenerator.nextId(),
            memberId = memberId,
            socialServiceUser = command.socialServiceUser,
            currentTime = command.currentTime
        )
        socialConnectionPersistencePort.save(socialConnection)
    }
}
