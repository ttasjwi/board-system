package com.ttasjwi.board.system.member.domain.external

import com.ttasjwi.board.system.member.domain.model.fixture.rawPasswordFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile

@SpringBootTest
@Profile("test")
@DisplayName("ExternalPasswordHandlerImpl 테스트")
class ExternalPasswordHandlerImplTest @Autowired constructor(
    private val externalPasswordHandler: ExternalPasswordHandler
) {

    @Nested
    @DisplayName("encode: 패스워드를 인코딩한다")
    inner class Encode {

        @Test
        fun test() {
            val rawPassword = rawPasswordFixture("1234")
            val encodedPassword = externalPasswordHandler.encode(rawPassword)
            assertThat(encodedPassword).isNotNull()
        }
    }

    @Nested
    @DisplayName("matches: 원본 패스워드와 인코딩 된 패스워드를 비교하여 일치하는 지 여부를 반환한다.")
    inner class Matches {

        @Test
        @DisplayName("원본 패스워드가 같으면, true를 반환한다.")
        fun testSamePassword() {
            // given
            val rawPassword = rawPasswordFixture("1234")
            val encodedPassword = externalPasswordHandler.encode(rawPassword)

            // when
            val matches = externalPasswordHandler.matches(rawPassword, encodedPassword)

            // then
            assertThat(matches).isTrue()
        }

        @Test
        @DisplayName("원본 패스워드가 다르면, false를 반환한다.")
        fun testDifferentPassword() {
            // given
            val rawPassword = rawPasswordFixture("1234")
            val encodedPassword = externalPasswordHandler.encode(rawPassword)

            // when
            val matches = externalPasswordHandler.matches(rawPasswordFixture("1235"), encodedPassword)

            // then
            assertThat(matches).isFalse()
        }
    }
}
