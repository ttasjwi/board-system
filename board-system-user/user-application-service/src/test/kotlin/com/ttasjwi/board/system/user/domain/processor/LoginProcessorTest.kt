package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.LoginCommand
import com.ttasjwi.board.system.user.domain.exception.LoginFailureException
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.user.domain.port.fixture.MemberRefreshTokenIdListPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.RefreshTokenIdPersistencePortFixture
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("LoginProcessor: 로그인 애플리케이션 명령을 실질적으로 처리한다")
class LoginProcessorTest {

    private lateinit var processor: LoginProcessor
    private lateinit var successCommand: LoginCommand
    private lateinit var savedMember: Member
    private lateinit var memberRefreshTokenIdListPersistencePortFixture: MemberRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenPersistencePortFixture: RefreshTokenIdPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()

        savedMember = container.memberPersistencePortFixture.save(
            memberFixture(
                memberId = 1543L,
                email = "hello@gmail.com",
                password = "1234",
                username = "username",
                nickname = "nickname",
                role = Role.USER,
                registeredAt = appDateTimeFixture(minute = 6)
            )
        )
        successCommand = LoginCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            currentTime = appDateTimeFixture(minute = 10)
        )
        memberRefreshTokenIdListPersistencePortFixture = container.memberRefreshTokenIdListPersistencePortFixture
        refreshTokenPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
        processor = container.loginProcessor
    }

    @Test
    @DisplayName("최초 로그인 시 액세스토큰/리프레시토큰이 생성되고, 사용자별 리프레시토큰이 저장된다.")
    fun testFirstLoginSuccess() {
        // given
        // when
        val (accessToken, refreshToken) = processor.login(successCommand)

        // then
        val memberRefreshTokenIds = memberRefreshTokenIdListPersistencePortFixture.findAll(refreshToken.memberId)
        val refreshTokenExists =
            refreshTokenPersistencePortFixture.exists(refreshToken.memberId, refreshToken.refreshTokenId)

        assertThat(accessToken.authUser.userId).isEqualTo(savedMember.memberId)
        assertThat(accessToken.authUser.role).isEqualTo(savedMember.role)
        assertThat(accessToken.issuedAt).isEqualTo(successCommand.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(successCommand.currentTime.plusMinutes(30))

        assertThat(refreshToken.memberId).isEqualTo(savedMember.memberId)
        assertThat(refreshToken.issuedAt).isEqualTo(successCommand.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(successCommand.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))
        assertThat(memberRefreshTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenExists).isTrue()
    }

    @Test
    @DisplayName("이메일에 대응하는 회원을 찾지 못 하면 로그인 실패 예외가 발생한다.")
    fun testMemberNotFound() {
        // given
        val command = LoginCommand(
            email = "jello@gmail.com",
            rawPassword = successCommand.rawPassword,
            currentTime = successCommand.currentTime
        )
        // when
        val exception = assertThrows<LoginFailureException> { processor.login(command) }

        // then
        assertThat(exception.debugMessage).isEqualTo("로그인 실패 - 일치하는 이메일(email=${command.email})의 회원을 찾지 못 함")
    }

    @Test
    @DisplayName("패스워드가 일치하지 않으면 로그인 실패 예외가 발생한다")
    fun testWrongPassword() {
        // given
        val command = LoginCommand(
            email = successCommand.email,
            rawPassword = "4321",
            currentTime = successCommand.currentTime
        )

        // when
        val exception = assertThrows<LoginFailureException> { processor.login(command) }

        // then
        assertThat(exception.debugMessage).isEqualTo("로그인 처리 실패 - 패스워드 불일치")
    }
}
