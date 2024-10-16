package com.ttasjwi.board.system.core.message

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

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

    override fun resolveMessage(code: String): String {
        val messageSource = selectMessageSource(code)
        return messageSource.getMessage("$code.message", null, LocaleContextHolder.getLocale())
    }

    override fun resolveDescription(code: String, args: List<Any?>): String {
        val messageSource = selectMessageSource(code)
        return messageSource.getMessage("$code.description", args.toTypedArray(), LocaleContextHolder.getLocale())
    }

    private fun selectMessageSource(code: String): MessageSource {
        return if (code.startsWith(ERROR_MESSAGE_PREFIX)) {
            errorMessageSource
        } else {
            generalMessageSource
        }
    }
}
