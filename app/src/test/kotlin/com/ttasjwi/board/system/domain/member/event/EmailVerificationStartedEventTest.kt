package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationStartedEvent 테스트")
class EmailVerificationStartedEventTest {

    @Test
    @DisplayName("이벤트 발생시점은 code 생성 시각과 같다")
    fun test1() {
        val codeCreatedAt = appDateTimeFixture()
        val event = emailVerificationStartedEventFixture(
            codeCreatedAt = codeCreatedAt
        )
        assertThat(event.occurredAt).isEqualTo(codeCreatedAt)
        assertThat(event.data.codeCreatedAt).isEqualTo(codeCreatedAt)
    }

    @Test
    @DisplayName("생성시점에 전달한 데이터를 가진다")
    fun test2() {
        val email = "kyaru@gmail.com"
        val code = "23456f"
        val codeCreatedAt = appDateTimeFixture(minute = 3)
        val codeExpiresAt = appDateTimeFixture(minute = 8)

        val event = emailVerificationStartedEventFixture(
            email = email,
            code = code,
            codeCreatedAt = codeCreatedAt,
            codeExpiresAt = codeExpiresAt
        )
        val data = event.data

        assertThat(data.email).isEqualTo(email)
        assertThat(data.code).isEqualTo(code)
        assertThat(data.codeCreatedAt).isEqualTo(codeCreatedAt)
        assertThat(data.codeExpiresAt).isEqualTo(codeExpiresAt)
    }

}
