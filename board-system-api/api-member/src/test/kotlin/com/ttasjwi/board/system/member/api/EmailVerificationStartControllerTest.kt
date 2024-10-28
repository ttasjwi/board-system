package com.ttasjwi.board.system.member.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("EmailVerificationStartController 테스트")
class EmailVerificationStartControllerTest {

    private lateinit var emailVerificationStartController: EmailVerificationStartController

    @BeforeEach
    fun setup() {
        emailVerificationStartController = EmailVerificationStartController()
    }

    @Test
    @DisplayName("이 api는 현재 미구현 상태이다.")
    fun test() {
        // given
        // when
        // then
        assertThrows<NotImplementedError> { emailVerificationStartController.startEmailVerification() }
    }
}
