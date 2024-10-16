package com.ttasjwi.board.system.core.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("MessageResolverImpl 작동 테스트")
class MessageResolverImplTest @Autowired constructor(
    private val messageResolver: MessageResolver
) {

    @Test
    @DisplayName("일반 메시지 - 한국어 테스트")
    fun generalMessageKoreanTest() {
        LocaleContextHolder.setLocale(Locale.KOREAN)

        val code = "Example"

        val message = messageResolver.resolveMessage(code)
        val description = messageResolver.resolveDescription(code, listOf(1, 2, "야옹"))

        assertThat(message).isEqualTo("예제 메시지")
        assertThat(description).isEqualTo("예제 설명(args=1,2,야옹)")
    }

    @Test
    @DisplayName("일반 메시지 - 영어 테스트")
    fun generalMessageEnglishTest() {
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val code = "Example"

        val message = messageResolver.resolveMessage(code)
        val description = messageResolver.resolveDescription(code, listOf(1, 2, "nyaa"))

        assertThat(message).isEqualTo("Example Message")
        assertThat(description).isEqualTo("Example Description(args=1,2,nyaa)")
    }

    @Test
    @DisplayName("에러 메시지 - 한국어 테스트")
    fun errorMessageKoreanTest() {
        LocaleContextHolder.setLocale(Locale.KOREAN)

        val code = "Error.NullArgument"

        val message = messageResolver.resolveMessage(code)
        val description = messageResolver.resolveDescription(code, listOf("username"))

        assertThat(message).isEqualTo("필수값 누락")
        assertThat(description).isEqualTo("'username'은(는) 필수입니다.")
    }

    @Test
    @DisplayName("에러 메시지 - 영어 테스트")
    fun errorMessageEnglishTest() {
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val code = "Error.NullArgument"

        val message = messageResolver.resolveMessage(code)
        val description = messageResolver.resolveDescription(code, listOf("username"))

        assertThat(message).isEqualTo("Missing required value")
        assertThat(description).isEqualTo("The field 'username' is required.")
    }
}
