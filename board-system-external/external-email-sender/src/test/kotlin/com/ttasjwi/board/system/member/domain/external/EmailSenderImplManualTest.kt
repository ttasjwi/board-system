package com.ttasjwi.board.system.member.domain.external

import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.EmailSender
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Disabled // 수동테스트 용이므로, 로컬에서 수동테스트를 하고 싶다면 이 부분을 주석처리
@SpringBootTest
@DisplayName("EmailSenderImpl 수동 테스트")
class EmailSenderImplManualTest @Autowired constructor(
    private val emailSender: EmailSender
) {

    @Test
    @DisplayName("이메일이 전송되어 잘 보내져야한다.")
    fun test() {
        val email = emailFixture("ttasjwi920@gmail.com")
        val subject = "테스트 이메일"
        val content = "땃쥐에게 이메일이 갔어요!"
        emailSender.send(email, subject, content)
    }
}
