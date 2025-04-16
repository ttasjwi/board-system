package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialServiceUserFixture
import com.ttasjwi.board.system.user.domain.port.fixture.MemberPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.MemberRefreshTokenIdListPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.RefreshTokenIdPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.SocialConnectionPersistencePortFixture
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginProcessor: 소셜 로그인 애플리케이션 명령을 실질적으로 처리한다")
class SocialLoginProcessorTest {

    private lateinit var socialLoginProcessor: SocialLoginProcessor
    private lateinit var memberPersistencePortFixture: MemberPersistencePortFixture
    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture
    private lateinit var memberRefreshTokenIdListPersistencePortFixture: MemberRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        memberPersistencePortFixture = container.memberPersistencePortFixture
        socialConnectionPersistencePortFixture = container.socialConnectionPersistencePortFixture
        socialLoginProcessor = container.socialLoginProcessor
        memberRefreshTokenIdListPersistencePortFixture = container.memberRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
    }


    @Test
    @DisplayName("소셜 연동에 해당하는 회원이 있으면, 로그인 처리한다.")
    fun test1() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val member = memberPersistencePortFixture.save(memberFixture(email = email))
        socialConnectionPersistencePortFixture.save(
            socialConnectionFixture(
                id = 15567L,
                memberId = member.memberId,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdMember, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val memberTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(member.memberId)
        assertThat(createdMember).isNull()
        assertThat(accessToken.authMember.memberId).isEqualTo(member.memberId)
        assertThat(accessToken.authMember.role).isEqualTo(member.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))

        assertThat(refreshToken.memberId).isEqualTo(member.memberId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(memberTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenIdPersistencePortFixture.exists(member.memberId, refreshToken.refreshTokenId)).isTrue()
    }

    @Test
    @DisplayName("소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 생성 후 로그인 처리한다.")
    fun test2() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val member = memberPersistencePortFixture.save(memberFixture(email = email))

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdMember, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val memberTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(member.memberId)
        val findSocialConnection = socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!

        assertThat(createdMember).isNull()
        assertThat(accessToken.authMember.memberId).isEqualTo(member.memberId)
        assertThat(accessToken.authMember.role).isEqualTo(member.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))
        assertThat(refreshToken.memberId).isEqualTo(member.memberId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(memberTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenIdPersistencePortFixture.exists(member.memberId, refreshToken.refreshTokenId)).isTrue()

        assertThat(findSocialConnection.socialConnectionId).isNotNull
        assertThat(findSocialConnection.memberId).isEqualTo(member.memberId)
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
    }

    @Test
    @DisplayName("소셜 연동도 없고, 이메일에 해당하는 회원이 없으면, 회원 가입 및 소셜 연동 생성 후 로그인 처리한다.")
    fun test3() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdMember, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val findSocialConnection = socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!
        val findMember = memberPersistencePortFixture.findByIdOrNull(findSocialConnection.memberId)!!
        val memberTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(createdMember!!.memberId)

        assertThat(createdMember).isNotNull
        assertThat(createdMember.memberId).isNotNull()
        assertThat(createdMember.email).isEqualTo(command.email)
        assertThat(createdMember.username).isNotNull()
        assertThat(createdMember.nickname).isNotNull()
        assertThat(createdMember.password).isNotNull()
        assertThat(createdMember.registeredAt).isEqualTo(command.currentTime)
        assertThat(createdMember.role).isEqualTo(Role.USER)
        assertThat(findMember.memberId).isEqualTo(createdMember.memberId)
        assertThat(findMember.email).isEqualTo(createdMember.email)
        assertThat(findMember.username).isEqualTo(createdMember.username)
        assertThat(findMember.nickname).isEqualTo(createdMember.nickname)
        assertThat(findMember.password).isEqualTo(createdMember.password)
        assertThat(findMember.role).isEqualTo(createdMember.role)
        assertThat(findMember.registeredAt).isEqualTo(createdMember.registeredAt)
        assertThat(accessToken.authMember.memberId).isEqualTo(createdMember.memberId)
        assertThat(accessToken.authMember.role).isEqualTo(createdMember.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))
        assertThat(refreshToken.memberId).isEqualTo(createdMember.memberId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))
        assertThat(memberTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(
            refreshTokenIdPersistencePortFixture.exists(
                createdMember.memberId,
                refreshToken.refreshTokenId
            )
        ).isTrue()
        assertThat(findSocialConnection.socialConnectionId).isNotNull
        assertThat(findSocialConnection.memberId).isNotNull
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
    }
}
