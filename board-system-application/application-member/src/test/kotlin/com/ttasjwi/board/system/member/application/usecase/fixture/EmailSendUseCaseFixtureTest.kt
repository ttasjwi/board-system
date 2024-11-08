package com.ttasjwi.board.system.member.application.usecase.fixture

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailSendUseCase 픽스쳐 테스트")
class EmailSendUseCaseFixtureTest {

    private lateinit var emailSendUseCaseFixture: EmailSendUseCaseFixture

    @BeforeEach
    fun setup() {
        emailSendUseCaseFixture = EmailSendUseCaseFixture()
    }

    @Test
    fun sendEmail() {
        emailSendUseCaseFixture.sendEmail("ttasjwi@gmail.com", "제목", "내용")
    }
}
