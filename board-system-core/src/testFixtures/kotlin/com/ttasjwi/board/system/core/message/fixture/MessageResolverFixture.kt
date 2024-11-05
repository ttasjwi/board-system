package com.ttasjwi.board.system.core.message.fixture

import com.ttasjwi.board.system.core.message.MessageResolver
import java.util.*

class MessageResolverFixture : MessageResolver {

    override fun resolveMessage(code: String, locale: Locale): String {
        return "$code.message(locale=${locale})"
    }

    override fun resolveDescription(code: String, args: List<Any?>, locale: Locale): String {
        return "$code.description(args=$args,locale=$locale)"
    }
}
