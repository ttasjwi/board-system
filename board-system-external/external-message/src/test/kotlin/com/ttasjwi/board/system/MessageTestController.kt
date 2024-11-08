package com.ttasjwi.board.system

import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageTestController(
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @GetMapping("/api/test")
    fun test(): ResponseEntity<MessageTestResponse> {
        val locale = localeManager.getCurrentLocale()

        val code = "Example"
        val args = listOf(1, 2, 3)
        val message = messageResolver.resolve("$code.message", locale)
        val description = messageResolver.resolve("$code.description", locale, args)

        return ResponseEntity.ok(
            MessageTestResponse(
                locale = locale.toString(),
                code = code,
                message = message,
                description = description
            )
        )
    }

    class MessageTestResponse(
        val locale: String,
        val code: String,
        val message: String,
        val description: String
    )
}
