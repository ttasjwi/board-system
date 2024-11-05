package com.ttasjwi.board.system.core.message.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("MessageResolverFixture 테스트")
class MessageResolverFixtureTest {

    private val messageResolver = MessageResolverFixture()

    @Test
    @DisplayName("resolveMessage: code, locale 을 전달하여 메시지를 얻어올 수 있다.")
    fun testGeneralTitle() {
        val code = "hello"
        val locale = Locale.KOREAN

        val title = messageResolver.resolveMessage(code, locale)
        assertThat(title).isEqualTo("$code.message(locale=$locale)")
    }

    @Test
    @DisplayName("resolveDescription: code, args, locale 을 전달하여 메시지를 얻어올 수 있다.")
    fun testDescription() {
        val code = "hello"
        val args = listOf(1, 2)
        val locale = Locale.ENGLISH
        val description = messageResolver.resolveDescription(code, args, locale)
        assertThat(description).isEqualTo("$code.description(args=[1, 2],locale=$locale)")
    }
}
