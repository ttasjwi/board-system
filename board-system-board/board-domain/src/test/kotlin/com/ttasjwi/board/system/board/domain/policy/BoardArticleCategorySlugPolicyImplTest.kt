package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardArticleCategorySlugFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("BoardArticleSlugPolicyImpl: 게시글 카테고리 슬러그 도메인 정책")
class BoardArticleSlugPolicyImplTest {

    private lateinit var boardArticleCategorySlugPolicy: BoardArticleCategorySlugPolicy

    @BeforeEach
    fun setup() {
        boardArticleCategorySlugPolicy = BoardArticleCategorySlugPolicyImpl()
    }

    @Nested
    @DisplayName("validate: 유효성 검증 후, 유효하면 값을 그대로 담은 Result/ 유효하지 않으면 예외를 담은 Reuslt 를 반환한다.")
    inner class Validate {

        @Test
        @DisplayName("길이 유효성: 양끝 공백을 제거했을 때 ${BoardArticleCategorySlugPolicyImpl.MIN_LENGTH}자 이상, ${BoardArticleCategorySlugPolicyImpl.MAX_LENGTH}자 이하")
        fun test1() {
            // given
            val minLengthValue = "a".repeat(BoardArticleCategorySlugPolicyImpl.MIN_LENGTH)
            val maxLengthValue = "a".repeat(BoardArticleCategorySlugPolicyImpl.MAX_LENGTH)

            // when
            val minLengthBoardName = boardArticleCategorySlugPolicy.validate(minLengthValue).getOrThrow()
            val maxLengthBoardName = boardArticleCategorySlugPolicy.validate(maxLengthValue).getOrThrow()

            // then
            assertThat(minLengthBoardName).isEqualTo(minLengthValue)
            assertThat(maxLengthBoardName).isEqualTo(maxLengthValue)
        }

        @Test
        @DisplayName("양 끝 공백을 허용하지 않는다")
        fun test2() {
            val value = " ab0 "
            assertThrows<InvalidBoardArticleCategorySlugFormatException> { boardArticleCategorySlugPolicy.validate(value).getOrThrow() }
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun test3() {
            val value = "a".repeat(BoardArticleCategorySlugPolicyImpl.MIN_LENGTH - 1)
            assertThrows<InvalidBoardArticleCategorySlugFormatException> { boardArticleCategorySlugPolicy.validate(value).getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test4() {
            val value = "a".repeat(BoardArticleCategorySlugPolicyImpl.MAX_LENGTH + 1)

            assertThrows<InvalidBoardArticleCategorySlugFormatException> { boardArticleCategorySlugPolicy.validate(value).getOrThrow() }
        }


        @ParameterizedTest
        @ValueSource(strings = ["abadfc", "1asSf4", "Abc4"])
        @DisplayName("양 끝 공백을 제거했을 때, 영어, 숫자만 허용한다.")
        fun test5(value: String) {
            // given
            // when
            val name = boardArticleCategorySlugPolicy.validate(value).getOrThrow()

            // then
            assertThat(name).isEqualTo(value)
        }

        @ParameterizedTest
        @ValueSource(strings = ["ㅇㅅ4ㅇ", "ㅔㅔㅔ", "ㅠㅠ<ㅠ", "a!asㅔ잉"])
        @DisplayName("한글이나 특수문자 등, 영어/숫자가 아닌 문자는 허용되지 않는다.")
        fun test6(value: String) {
            assertThrows<InvalidBoardArticleCategorySlugFormatException> { boardArticleCategorySlugPolicy.validate(value).getOrThrow() }
        }

    }
}
