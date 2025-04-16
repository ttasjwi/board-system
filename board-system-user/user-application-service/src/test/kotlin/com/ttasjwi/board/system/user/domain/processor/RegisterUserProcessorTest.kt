package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.RegisterUserCommand
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserEmailException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserNicknameException
import com.ttasjwi.board.system.user.domain.exception.DuplicateUserUsernameException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.UserPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.PasswordEncryptionPortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("RegisterUserProcessor: 회원가입 명령을 처리하는 애플리케이션 처리자")
class RegisterUserProcessorTest {

    private lateinit var processor: RegisterUserProcessor
    private lateinit var userPersistencePortFixture: UserPersistencePortFixture
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture
    private lateinit var passwordEncryptionPortFixture: PasswordEncryptionPortFixture
    private lateinit var registeredUser: User

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        userPersistencePortFixture = container.userPersistencePortFixture
        emailVerificationPersistencePortFixture = container.emailVerificationPersistencePortFixture
        passwordEncryptionPortFixture = container.passwordEncryptionPortFixture
        processor = container.registerUserProcessor
        registeredUser = userPersistencePortFixture.save(
            userFixture(
                userId = 12345L,
                email = "registered@gmail.com",
                username = "registered",
                nickname = "가입된회원쟝",
            )
        )
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        val email = "hello@gmail.com"
        val currentTime = appDateTimeFixture(minute = 6)
        emailVerificationPersistencePortFixture.save(
            emailVerificationFixtureVerified(
                email = email,
                code = "code",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5),
                verifiedAt = appDateTimeFixture(minute = 3),
                verificationExpiresAt = appDateTimeFixture(minute = 33),
            ), appDateTimeFixture(minute = 33)
        )

        val command = RegisterUserCommand(
            email = email,
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = currentTime
        )

        // when
        val createdUser = processor.register(command)

        // then
        val findUser = userPersistencePortFixture.findByIdOrNull(createdUser.userId)!!
        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull(email)

        assertThat(createdUser.userId).isNotNull()
        assertThat(createdUser.email).isEqualTo(command.email)
        assertThat(createdUser.password).isEqualTo(passwordEncryptionPortFixture.encode(command.rawPassword))
        assertThat(createdUser.username).isEqualTo(command.username)
        assertThat(createdUser.nickname).isEqualTo(command.nickname)
        assertThat(createdUser.role).isEqualTo(Role.USER)
        assertThat(createdUser.registeredAt).isEqualTo(command.currentTime)

        assertThat(findEmailVerification).isNull()
        assertThat(findUser.userId).isEqualTo(createdUser.userId)
        assertThat(findUser.email).isEqualTo(createdUser.email)
        assertThat(findUser.password).isEqualTo(createdUser.password)
        assertThat(findUser.username).isEqualTo(createdUser.username)
        assertThat(findUser.nickname).isEqualTo(createdUser.nickname)
        assertThat(findUser.role).isEqualTo(createdUser.role)
        assertThat(findUser.registeredAt).isEqualTo(createdUser.registeredAt)
    }

    @Test
    @DisplayName("중복되는 이메일의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateEmail() {
        val command = RegisterUserCommand(
            email = registeredUser.email,
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateUserEmailException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 사용자 아이디(username)의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateUsername() {
        val command = RegisterUserCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = registeredUser.username,
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateUserUsernameException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 닉네임의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateNickname() {
        val command = RegisterUserCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = "testuser",
            nickname = registeredUser.nickname,
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateUserNicknameException> { processor.register(command) }
    }

    @Test
    @DisplayName("이메일 인증을 조회하지 못 했을 경우(만료됐거나, 없음) 예외가 발생한다.")
    fun testEmailVerificationNotFound() {
        val command = RegisterUserCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )
        assertThrows<EmailVerificationNotFoundException> { processor.register(command) }
    }
}
