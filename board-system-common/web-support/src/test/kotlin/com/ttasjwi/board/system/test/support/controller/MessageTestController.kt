package com.ttasjwi.board.system.test.support.controller

import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageTestController(
    private val messageResolver: MessageResolver,
    private val localeResolver: LocaleResolver
) {

    @GetMapping("/api/v1/test/message")
    fun testMessage(): String {
        return ""
    }
}
