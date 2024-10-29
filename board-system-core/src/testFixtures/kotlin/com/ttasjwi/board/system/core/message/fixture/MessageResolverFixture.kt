package com.ttasjwi.board.system.core.message.fixture

import com.ttasjwi.board.system.core.message.MessageResolver

class MessageResolverFixture : MessageResolver {

    override fun resolveMessage(code: String): String {
        return "$code.message"
    }

    override fun resolveDescription(code: String, args: List<Any?>): String {
        return "$code.description(args=$args)"
    }
}
