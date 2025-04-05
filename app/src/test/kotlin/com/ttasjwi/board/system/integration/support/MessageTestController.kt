package com.ttasjwi.board.system.integration.support

import com.ttasjwi.board.system.global.locale.LocaleManager
import com.ttasjwi.board.system.global.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 메시지 기능 동작 확인용 컨트롤러
 */
@RestController
class MessageTestController(
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @GetMapping("/api/v1/test/message")
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
