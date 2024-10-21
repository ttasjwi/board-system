package com.ttasjwi.board.system.core.message.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("MessageResolverFixture 테스트")
class MessageResolverFixtureTest {

    private val messageResolver = MessageResolverFixture()

    @Test
    @DisplayName("resolveMessage: code를 전달하면 code 뒤에 code.message 을 반환한다.")
    fun testGeneralTitle() {
        val code = "hello"
        val title = messageResolver.resolveMessage(code)
        assertThat(title).isEqualTo("$code.message")
    }

    @Test
    @DisplayName("resolveDescription: code와 args를 전달하면 `code.description(args=[args원소들])'을 반환한다.")
    fun testDescription() {
        val code = "hello"
        val args = listOf(1, 2)
        val description = messageResolver.resolveDescription(code, args)
        assertThat(description).isEqualTo("$code.description(args=[1, 2])")
    }

    @Test
    @DisplayName("resolveDescription: code만 전달하면 `code.description(args=[])'을 반환한다.")
    fun testDescriptionOnlyCode() {
        val code = "hello"
        val description = messageResolver.resolveDescription(code)
        assertThat(description).isEqualTo("$code.description(args=[])")
    }

    @Test
    @DisplayName("resolveDescription: code와 args(빈리스트)를 전달하면 `code.description(args=[])'을 반환한다.")
    fun testDescriptionWhenArgsNull() {
        val code = "hello"
        val description = messageResolver.resolveDescription(code, emptyList())
        assertThat(description).isEqualTo("$code.description(args=[])")
    }
}
