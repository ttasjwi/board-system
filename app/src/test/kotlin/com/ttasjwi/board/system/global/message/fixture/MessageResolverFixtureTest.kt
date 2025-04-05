package com.ttasjwi.board.system.global.message.fixture

import com.ttasjwi.board.system.global.message.fixture.MessageResolverFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("MessageResolverFixture 테스트")
class MessageResolverFixtureTest {

    private val messageResolver = MessageResolverFixture()

    @Test
    @DisplayName("resolve: code, locale 만 전달하여 메시지를 얻어올 수 있다.")
    fun test1() {
        val code = "hello"
        val locale = Locale.KOREAN

        val title = messageResolver.resolve(code, locale)
        assertThat(title).isEqualTo("$code(locale=$locale,args=[])")
    }

    @Test
    @DisplayName("resolve: code, locale, args 를 전달하여 메시지를 얻어올 수 있다.")
    fun test2() {
        val code = "hello"
        val locale = Locale.ENGLISH
        val args = listOf(1, 2)
        val description = messageResolver.resolve(code, locale, args)
        assertThat(description).isEqualTo("$code(locale=$locale,args=[1, 2])")
    }
}
