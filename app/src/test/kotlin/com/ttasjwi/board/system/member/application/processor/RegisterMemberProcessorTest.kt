package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.application.dto.RegisterMemberCommand
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberEmailException
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberNicknameException
import com.ttasjwi.board.system.member.application.exception.DuplicateMemberUsernameException
import com.ttasjwi.board.system.member.application.exception.EmailVerificationNotFoundException
import com.ttasjwi.board.system.member.domain.model.Member
import com.ttasjwi.board.system.member.domain.model.fixture.*
import com.ttasjwi.board.system.member.domain.service.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("RegisterMemberProcessor: 회원가입 명령을 처리하는 애플리케이션 처리자")
class RegisterMemberProcessorTest {

    private lateinit var processor: RegisterMemberProcessor
    private lateinit var memberStorageFixture: MemberStorageFixture
    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture
    private lateinit var registeredMember: Member

    @BeforeEach
    fun setup() {
        memberStorageFixture = MemberStorageFixture()
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
        processor = RegisterMemberProcessor(
            memberFinder = memberStorageFixture,
            emailVerificationFinder = emailVerificationStorageFixture,
            emailVerificationHandler = EmailVerificationHandlerFixture(),
            emailVerificationAppender = emailVerificationStorageFixture,
            memberCreator = MemberCreatorFixture(),
            memberAppender = memberStorageFixture,
            memberEventCreator = MemberEventCreatorFixture()
        )
        registeredMember = memberStorageFixture.save(
            memberFixture(
                id = 12345L,
                email = "registered@gmail.com",
                username = "registered",
                nickname = "가입된회원쟝",
            )
        )
    }

    @Test
    @DisplayName("성공 테스트")
    fun testSuccess() {
        val email = emailFixture("hello@gmail.com")
        val currentTime = appDateTimeFixture(minute = 6)
        emailVerificationStorageFixture.append(
            emailVerificationFixtureVerified(
                email = email.value,
                code = "code",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5),
                verifiedAt = appDateTimeFixture(minute = 3),
                verificationExpiresAt = appDateTimeFixture(minute = 33),
            ), appDateTimeFixture(minute = 33)
        )

        val command = RegisterMemberCommand(
            email = email,
            rawPassword = rawPasswordFixture("1234"),
            username = usernameFixture("testuser"),
            nickname = nicknameFixture("testnick"),
            currentTime = currentTime
        )

        // when
        val event = processor.register(command)

        // then
        val data = event.data
        val findMember = memberStorageFixture.findByIdOrNull(data.memberId)!!
        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(email)

        assertThat(event.occurredAt).isEqualTo(currentTime)
        assertThat(data.memberId).isNotNull()
        assertThat(data.email).isEqualTo(email.value)
        assertThat(data.username).isEqualTo(command.username.value)
        assertThat(data.nickname).isEqualTo(command.nickname.value)
        assertThat(data.roleName).isEqualTo(findMember.role.name)
        assertThat(data.registeredAt).isEqualTo(currentTime)

        assertThat(findEmailVerification).isNull()
        assertThat(findMember.id).isEqualTo(data.memberId)
        assertThat(findMember.email).isEqualTo(command.email)
        assertThat(findMember.password.value).isEqualTo(command.rawPassword.value)
        assertThat(findMember.username).isEqualTo(command.username)
        assertThat(findMember.nickname).isEqualTo(command.nickname)
        assertThat(findMember.role).isNotNull()
        assertThat(findMember.registeredAt).isEqualTo(currentTime)
    }

    @Test
    @DisplayName("중복되는 이메일의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateEmail() {
        val command = RegisterMemberCommand(
            email = registeredMember.email,
            rawPassword = rawPasswordFixture("1234"),
            username = usernameFixture("testuser"),
            nickname = nicknameFixture("testnick"),
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberEmailException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 사용자 아이디(username)의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateUsername() {
        val command = RegisterMemberCommand(
            email = emailFixture("hello@gmail.com"),
            rawPassword = rawPasswordFixture("1234"),
            username = registeredMember.username,
            nickname = nicknameFixture("testnick"),
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberUsernameException> { processor.register(command) }
    }

    @Test
    @DisplayName("중복되는 닉네임의 회원이 존재하면 예외가 발생한다")
    fun testDuplicateNickname() {
        val command = RegisterMemberCommand(
            email = emailFixture("hello@gmail.com"),
            rawPassword = rawPasswordFixture("1234"),
            username = usernameFixture("testuser"),
            nickname = registeredMember.nickname,
            currentTime = appDateTimeFixture(minute = 6)
        )

        assertThrows<DuplicateMemberNicknameException> { processor.register(command) }
    }

    @Test
    @DisplayName("이메일 인증을 조회하지 못 했을 경우(만료됐거나, 없음) 예외가 발생한다.")
    fun testEmailVerificationNotFound() {
        val command = RegisterMemberCommand(
            email = emailFixture("hello@gmail.com"),
            rawPassword = rawPasswordFixture("1234"),
            username = usernameFixture("testuser"),
            nickname = nicknameFixture("testnick"),
            currentTime = appDateTimeFixture(minute = 6)
        )
        assertThrows<EmailVerificationNotFoundException> { processor.register(command) }
    }
}
