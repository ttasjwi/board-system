package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStorage(Appender, Finder) 테스트")
class EmailVerificationStorageTest {

    private lateinit var emailVerificationStorage: EmailVerificationStorage

    @BeforeEach
    fun setup() {
        emailVerificationStorage = EmailVerificationStorage()
    }

    @Test
    @DisplayName("이메일 인증을 저장하고 이메일로 다시 찾을 수 있다")
    fun appendAndFind() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationStorage.append(emailVerification, timeFixture())

        val findEmailVerification = emailVerificationStorage.findByEmailOrNull(emailVerification.email)!!

        assertThat(findEmailVerification.email).isEqualTo(emailVerification.email)
        assertThat(findEmailVerification.code).isEqualTo(emailVerification.code)
        assertThat(findEmailVerification.codeCreatedAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(findEmailVerification.codeExpiresAt).isEqualTo(emailVerification.codeExpiresAt)
        assertThat(findEmailVerification.verifiedAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(findEmailVerification.verificationExpiresAt).isEqualTo(emailVerification.verificationExpiresAt)
    }

    @Test
    @DisplayName("이메일 인증을 찾지 못 했다면 null 이 반환된다")
    fun notFoundNull() {
        val findEmailVerification = emailVerificationStorage.findByEmailOrNull(emailFixture("bye@gmail.com"))
        assertThat(findEmailVerification).isNull()
    }

    @Test
    @DisplayName("remove 테스트")
    fun remove() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationStorage.append(emailVerification, timeFixture())

        emailVerificationStorage.removeByEmail(emailVerification.email)

        val findEmailVerification = emailVerificationStorage.findByEmailOrNull(emailVerification.email)
        assertThat(findEmailVerification).isNull()
    }

}
