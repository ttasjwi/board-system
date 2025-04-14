package com.ttasjwi.board.system.common.infra.websupport.message

import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.context.MessageSource
import java.util.*

internal class DefaultMessageResolver(
    private val generalMessageSource: MessageSource,
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
