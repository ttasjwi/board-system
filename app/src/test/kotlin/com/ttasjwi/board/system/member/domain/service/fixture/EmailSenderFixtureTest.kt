package com.ttasjwi.board.system.member.domain.service.fixture

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailSender 픽스쳐 테스트")
class EmailSenderFixtureTest {

    private lateinit var emailSenderFixture: EmailSenderFixture

    @BeforeEach
    fun setup() {
        emailSenderFixture = EmailSenderFixture()
    }

    @Test
    @DisplayName("동작 테스트")
    fun send() {
        emailSenderFixture.send("ttasjwi@gmail.com", "제목", "내용")
    }
}
