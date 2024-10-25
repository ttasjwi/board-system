package com.ttasjwi.board.system.member.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerificationController 테스트")
class EmailVerificationControllerTest {

    private lateinit var emailVerificationController: EmailVerificationController

    @BeforeEach
    fun setup() {
        emailVerificationController = EmailVerificationController()
    }

    @Test
    @DisplayName("이 api는 현재 미구현 상태이다.")
    fun test() {
        // given
        // when
        // then
        assertThrows<NotImplementedError> { emailVerificationController.emailVerification() }
    }
}
