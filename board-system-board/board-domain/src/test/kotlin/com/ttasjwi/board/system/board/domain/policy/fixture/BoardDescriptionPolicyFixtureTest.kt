package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.BoardDescriptionPolicy
import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("[board-domain] BoardDescriptionPolicyFixture 테스트")
class BoardDescriptionPolicyFixtureTest {

    private lateinit var boardDescriptionPolicyFixture: BoardDescriptionPolicy

    @BeforeEach
    fun setup() {
        boardDescriptionPolicyFixture = BoardDescriptionPolicyFixture()
    }

    @Nested
    @DisplayName("게시판 설명 포맷을 검증한다.")
    inner class Validate {


        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "설명"

            // when
            val description = boardDescriptionPolicyFixture.create(value).getOrThrow()

            // then
            assertThat(description).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardDescriptionPolicyFixture.ERROR_DESCRIPTION

            // when
            val exception = assertThrows<CustomException> { boardDescriptionPolicyFixture.create(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 설명 포맷 예외 - 테스트")
        }
    }
}
