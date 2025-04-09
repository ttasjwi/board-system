package com.ttasjwi.board.system.common.websupport.message

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.MessageSource
import java.util.*

@DisplayName("DefaultMessageResolver 작동 테스트")
class DefaultMessageResolverTest {

    private lateinit var messageResolver: DefaultMessageResolver
    private lateinit var generalMessageSource: MessageSource
    private lateinit var errorMessageSource: MessageSource

    @BeforeEach
    fun setup() {
        generalMessageSource = mockk<MessageSource>()
        errorMessageSource = mockk<MessageSource>()
        messageResolver = DefaultMessageResolver(
            generalMessageSource = generalMessageSource,
            errorMessageSource = errorMessageSource
        )
    }

    @Test
    @DisplayName("일반 메시지 - 한국어 테스트")
    fun generalMessageKoreanTest() {
        val code = "Example"
        val locale = Locale.KOREAN
        val args = listOf(1, 2, "야옹")

        every { generalMessageSource.getMessage(code, any(), locale) } returns "Example General Message"

        val message = messageResolver.resolve(code, locale, args)


        assertThat(message).isEqualTo("Example General Message")
        verify(exactly = 1) { generalMessageSource.getMessage(code, any(), locale) }
        verify(exactly = 0) { errorMessageSource.getMessage(anyNullable(), anyNullable(), anyNullable()) }
    }

    @Test
    @DisplayName("에러 메시지 - 한국어 테스트")
    fun errorMessageKoreanTest() {
        val code = "Error.Example"
        val locale = Locale.KOREAN
        val args = listOf(1, 2, "hello")

        every { errorMessageSource.getMessage(code, any(), locale) } returns "Example Error Message"

        val message = messageResolver.resolve(code, locale, args)

        assertThat(message).isEqualTo("Example Error Message")
        verify(exactly = 0) { generalMessageSource.getMessage(anyNullable(), anyNullable(), anyNullable()) }
        verify(exactly = 1) { errorMessageSource.getMessage(code, any(), locale) }
    }

}
