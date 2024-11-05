package com.ttasjwi.board.system.core.message

import java.util.Locale

interface MessageResolver {
    /**
     * code 에 대응하는 메시지를 찾습니다.
     */
    fun resolveMessage(code: String, locale: Locale): String

    /**
     * code 에 대응하는 메시지 설명(description) 을 찾습니다.
     */
    fun resolveDescription(
        code: String,
        args: List<Any?> = emptyList(),
        locale: Locale,
    ): String
}
