package com.ttasjwi.board.system

import com.ttasjwi.board.system.core.message.MessageResolver
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MessageTestController(
    private val messageResolver: MessageResolver
) {

    @GetMapping("/api/test")
    fun test(): ResponseEntity<MessageTestResponse> {
        val locale = LocaleContextHolder.getLocale()

        val code = "Example"
        val args = listOf(1, 2, 3)
        val message = messageResolver.resolveMessage(code)
        val description = messageResolver.resolveDescription(code, args)

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
