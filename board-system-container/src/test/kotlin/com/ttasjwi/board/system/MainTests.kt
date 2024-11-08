package com.ttasjwi.board.system

import com.ttasjwi.board.system.member.domain.service.EmailSender
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class MainTests {

    @MockBean
    private lateinit var emailSender: EmailSender

    @Test
    fun contextLoads() {
    }

}
