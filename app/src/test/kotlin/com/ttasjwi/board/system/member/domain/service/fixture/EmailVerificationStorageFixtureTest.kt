package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStorageFixture(Appender, Finder) 테스트")
class EmailVerificationStorageFixtureTest {

    private lateinit var emailVerificationStorageFixture: EmailVerificationStorageFixture

    @BeforeEach
    fun setup() {
        emailVerificationStorageFixture = EmailVerificationStorageFixture()
    }

    @Test
    @DisplayName("이메일 인증을 저장하고 이메일로 다시 찾을 수 있다")
    fun appendAndFind() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationStorageFixture.append(emailVerification, timeFixture())

        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(emailVerification.email)!!

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
        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(emailFixture("bye@gmail.com"))
        assertThat(findEmailVerification).isNull()
    }

    @Test
    @DisplayName("remove 테스트")
    fun remove() {
        val emailVerification = emailVerificationFixtureVerified()
        emailVerificationStorageFixture.append(emailVerification, timeFixture())

        emailVerificationStorageFixture.removeByEmail(emailVerification.email)

        val findEmailVerification = emailVerificationStorageFixture.findByEmailOrNull(emailVerification.email)
        assertThat(findEmailVerification).isNull()
    }

}
