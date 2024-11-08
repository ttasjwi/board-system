package com.ttasjwi.board.system.core.message

import java.util.Locale

interface MessageResolver {
    /**
     * code 에 대응하는 메시지를 찾습니다.
     */
    fun resolve(code: String, locale: Locale, args: List<Any?> = emptyList()): String
}
