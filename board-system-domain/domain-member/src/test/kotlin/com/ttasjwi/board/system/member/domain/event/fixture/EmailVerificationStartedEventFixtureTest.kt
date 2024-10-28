package com.ttasjwi.board.system.member.domain.event.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStartedEventFixture 테스트")
class EmailVerificationStartedEventFixtureTest {

    @Test
    @DisplayName("인자를 전달하지 않아도 기본값들을 가지고 있다.")
    fun test1() {
        val event = emailVerificationStartedEventFixture()
        val data = event.data

        assertThat(event.occurredAt).isNotNull()
        assertThat(data.email).isNotNull()
        assertThat(data.code).isNotNull()
        assertThat(data.codeCreatedAt).isNotNull()
        assertThat(data.codeExpiresAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀하게 필드를 지정할 수 있다.")
    fun test2() {
        val email = "fire@gmail.com"
        val code = "54321"
        val codeCreatedAt = timeFixture(minute = 5)
        val codeExpiresAt = timeFixture(minute = 10)

        val event = emailVerificationStartedEventFixture(
            email = email,
            code = code,
            codeCreatedAt = codeCreatedAt,
            codeExpiresAt = codeExpiresAt
        )
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(codeCreatedAt)
        assertThat(data.email).isEqualTo(email)
        assertThat(data.code).isEqualTo(code)
        assertThat(data.codeCreatedAt).isEqualTo(codeCreatedAt)
        assertThat(data.codeExpiresAt).isEqualTo(codeExpiresAt)
    }
}
