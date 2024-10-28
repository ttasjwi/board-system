package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.rawPasswordFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RawPassword : 원본 패스워드")
class RawPasswordTest {

    @Test
    @DisplayName("toString: 패스워드 문자열을 디버깅용으로 출력하려 시도하면, 암호는 노출되지 않는다.")
    fun testToString() {
        val password = rawPasswordFixture("1234")
        assertThat(password.toString()).isEqualTo("RawPassword(value=[SECRET])")
    }

}
