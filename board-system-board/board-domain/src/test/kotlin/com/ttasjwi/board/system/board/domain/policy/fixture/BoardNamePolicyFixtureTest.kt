package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.BoardNamePolicy
import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardNamePolicyFixture 테스트")
class BoardNamePolicyFixtureTest {

    private lateinit var boardNamePolicyFixture: BoardNamePolicy

    @BeforeEach
    fun setup() {
        boardNamePolicyFixture = BoardNamePolicyFixture()
    }

    @Nested
    @DisplayName("게시판 이름을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "경제"

            // when
            val name = boardNamePolicyFixture.validate(value).getOrThrow()

            // then
            assertThat(name).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardNamePolicyFixture.ERROR_NAME

            // when
            val exception = assertThrows<CustomException> { boardNamePolicyFixture.validate(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 이름 포맷 예외 - 테스트")
        }
    }
}
