package com.ttasjwi.board.system.common.websupport.test.support.controller

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageTestController(
    private val messageResolver: MessageResolver,
    private val localeResolver: LocaleResolver,
) {

    @PermitAll
    @GetMapping("/api/v1/test/web-support/message")
    fun generalMessageTest(
        @RequestParam("code") code: String,
    ): ResponseEntity<MessageTestResponse> {
        val locale = localeResolver.getCurrentLocale()
        val message = messageResolver.resolve(
            code = "${code}.message",
            locale = locale
        )
        val description = messageResolver.resolve(
            code = "${code}.description",
            locale = locale,
            args = listOf(1,2,3)
        )
        return ResponseEntity.ok(MessageTestResponse(locale.toString(), code, message, description))
    }
}

data class MessageTestResponse(
    val locale: String,
    val code: String,
    val message: String,
    val description: String,
)
