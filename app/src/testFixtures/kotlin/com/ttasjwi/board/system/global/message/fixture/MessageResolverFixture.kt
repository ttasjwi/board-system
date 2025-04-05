package com.ttasjwi.board.system.global.message.fixture

import com.ttasjwi.board.system.global.message.MessageResolver
import java.util.*

class MessageResolverFixture : MessageResolver {

    override fun resolve(code: String, locale: Locale, args: List<Any?>): String {
        return "$code(locale=$locale,args=$args)"
    }
}
