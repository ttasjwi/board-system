package com.ttasjwi.board.system.core.message

interface MessageResolver {
    /**
     * code 에 대응하는 메시지를 찾습니다.
     */
    fun resolveMessage(code: String): String

    /**
     * code 에 대응하는 메시지 설명(description) 을 찾습니다.
     */
    fun resolveDescription(
        code: String,
        args: List<Any?> = emptyList()
    ): String
}
