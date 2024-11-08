package com.ttasjwi.board.system.core.message

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class MessageResolverImpl(
    @Qualifier("generalMessageSource")
    private val generalMessageSource: MessageSource,

    @Qualifier("errorMessageSource")
    private val errorMessageSource: MessageSource,
) : MessageResolver {

    companion object {
        private const val ERROR_MESSAGE_PREFIX = "Error."
    }

    override fun resolve(code: String, locale: Locale, args: List<Any?>): String {
        val messageSource = selectMessageSource(code)
        return messageSource.getMessage(code, args.toTypedArray(), locale)
    }

    private fun selectMessageSource(code: String): MessageSource {
        return if (code.startsWith(ERROR_MESSAGE_PREFIX)) {
            errorMessageSource
        } else {
            generalMessageSource
        }
    }
}
