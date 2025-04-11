package com.ttasjwi.board.system.auth.application.processor

import com.ttasjwi.board.system.auth.application.dto.LoginCommand
import com.ttasjwi.board.system.auth.application.exception.LoginFailureException
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.PasswordManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("LoginProcessor: 로그인 애플리케이션 명령을 실질적으로 처리한다")
class LoginProcessorTest {

    private lateinit var processor: LoginProcessor
    private lateinit var refreshTokenHolderStorageFixture: RefreshTokenHolderStorageFixture
    private lateinit var successCommand: LoginCommand
    private lateinit var savedMember: Member

    @BeforeEach
    fun setup() {
        val memberStorageFixture = MemberStorageFixture()

        savedMember = memberStorageFixture.save(
            memberFixture(
                id = 1543L,
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

        refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()
        processor = LoginProcessor(
            memberFinder = memberStorageFixture,
            passwordManager = PasswordManagerFixture(),
            authMemberCreator = AuthMemberCreatorFixture(),
            accessTokenGenerator = AccessTokenGeneratorFixture(),
            refreshTokenManager = RefreshTokenManagerFixture(),
            refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
            refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
            refreshTokenHolderAppender = refreshTokenHolderStorageFixture,
            authEventCreator = AuthEventCreatorFixture()
        )
    }

    @Test
    @DisplayName("최초 로그인 시 리프레시토큰 홀더가 생성되고 저장되어 관리되며, 결과를 담은 이벤트가 반환된다.")
    fun testFirstLoginSuccess() {
        // given
        // when
        val event = processor.login(successCommand)

        // then
        val data = event.data
        val refreshTokenHolder = refreshTokenHolderStorageFixture.findByMemberIdOrNull(savedMember.id)!!
        val tokens = refreshTokenHolder.getTokens()

        assertThat(event.occurredAt).isEqualTo(successCommand.currentTime)
        assertThat(data.accessToken).isNotNull()
        assertThat(data.accessTokenExpiresAt).isNotNull()
        assertThat(data.refreshToken).isNotNull()
        assertThat(data.refreshTokenExpiresAt).isNotNull()
        assertThat(refreshTokenHolder.authMember.memberId).isEqualTo(savedMember.id)
        assertThat(refreshTokenHolder.authMember.role).isEqualTo(savedMember.role)
        assertThat(tokens.size).isEqualTo(1)
        assertThat(refreshTokenHolder.getTokens().values.map { it.tokenValue }).containsExactly(data.refreshToken)
    }

    @Test
    @DisplayName("2회차 로그인 시에는 기존 리프레시토큰 홀더를 조회해서 사용한다.")
    fun testSecondLoginSuccess() {
        // given
        val firstLoginCommand = successCommand
        val secondLoginCommand = LoginCommand(
            email = firstLoginCommand.email,
            rawPassword = firstLoginCommand.rawPassword,
            currentTime = appDateTimeFixture(minute = 12)
        )
        val firstLoginEventData = processor.login(firstLoginCommand).data

        // when
        val secondLoginEventData = processor.login(secondLoginCommand).data

        // then
        val refreshTokenHolder = refreshTokenHolderStorageFixture.findByMemberIdOrNull(savedMember.id)!!
        val tokens = refreshTokenHolder.getTokens()

        assertThat(tokens.size).isEqualTo(2)
        assertThat(tokens.values.map { it.tokenValue }).containsExactlyInAnyOrder(
            firstLoginEventData.refreshToken,
            secondLoginEventData.refreshToken
        )
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
