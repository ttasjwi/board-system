package com.ttasjwi.board.system.member.domain.model

/**
 * 사용자 관점 아이디
 */
class Username
internal constructor(
    val value: String
) {

    companion object {
        fun restore(value: String): Username {
            return Username(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Username) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Username(value=$value)"
    }
}
