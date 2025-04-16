package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.RegisterMemberCommand
import com.ttasjwi.board.system.user.domain.exception.DuplicateMemberEmailException
import com.ttasjwi.board.system.user.domain.exception.DuplicateMemberNicknameException
import com.ttasjwi.board.system.user.domain.exception.DuplicateMemberUsernameException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.user.domain.model.Member
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.user.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.user.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.MemberPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.PasswordEncryptionPortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("RegisterMemberProcessor: 회원가입 명령을 처리하는 애플리케이션 처리자")
class RegisterMemberProcessorTest {

    private lateinit var processor: RegisterMemberProcessor
    private lateinit var memberPersistencePortFixture: MemberPersistencePortFixture
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture
    private lateinit var passwordEncryptionPortFixture: PasswordEncryptionPortFixture
    private lateinit var registeredMember: Member

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        memberPersistencePortFixture = container.memberPersistencePortFixture
        emailVerificationPersistencePortFixture = container.emailVerificationPersistencePortFixture
        passwordEncryptionPortFixture = container.passwordEncryptionPortFixture
        processor = container.registerMemberProcessor
        registeredMember = memberPersistencePortFixture.save(
            memberFixture(
                memberId = 12345L,
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

        val command = RegisterMemberCommand(
            email = email,
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = currentTime
        )

        // when
        val createdMember = processor.register(command)

        // then
        val findMember = memberPersistencePortFixture.findByIdOrNull(createdMember.memberId)!!
        val findEmailVerification = emailVerificationPersistencePortFixture.findByEmailOrNull(email)

        assertThat(createdMember.memberId).isNotNull()
        assertThat(createdMember.email).isEqualTo(command.email)
        assertThat(createdMember.password).isEqualTo(passwordEncryptionPortFixture.encode(command.rawPassword))
        assertThat(createdMember.username).isEqualTo(command.username)
        assertThat(createdMember.nickname).isEqualTo(command.nickname)
        assertThat(createdMember.role).isEqualTo(Role.USER)
        assertThat(createdMember.registeredAt).isEqualTo(command.currentTime)

        assertThat(findEmailVerification).isNull()
        assertThat(findMember.memberId).isEqualTo(createdMember.memberId)
        assertThat(findMember.email).isEqualTo(createdMember.email)
        assertThat(findMember.password).isEqualTo(createdMember.password)
        assertThat(findMember.username).isEqualTo(createdMember.username)
        assertThat(findMember.nickname).isEqualTo(createdMember.nickname)
        assertThat(findMember.role).isEqualTo(createdMember.role)
        assertThat(findMember.registeredAt).isEqualTo(createdMember.registeredAt)
    }

    @Test
    @DisplayName("중복되는 이메일의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateEmail() {
        val command = RegisterMemberCommand(
            email = registeredMember.email,
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberEmailException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 사용자 아이디(username)의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateUsername() {
        val command = RegisterMemberCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = registeredMember.username,
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberUsernameException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 닉네임의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateNickname() {
        val command = RegisterMemberCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = "testuser",
            nickname = registeredMember.nickname,
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberNicknameException> { processor.register(command) }
    }

    @Test
    @DisplayName("이메일 인증을 조회하지 못 했을 경우(만료됐거나, 없음) 예외가 발생한다.")
    fun testEmailVerificationNotFound() {
        val command = RegisterMemberCommand(
            email = "hello@gmail.com",
            rawPassword = "1234",
            username = "testuser",
            nickname = "testnick",
            currentTime = appDateTimeFixture(minute = 6)
        )
        assertThrows<EmailVerificationNotFoundException> { processor.register(command) }
    }
}
