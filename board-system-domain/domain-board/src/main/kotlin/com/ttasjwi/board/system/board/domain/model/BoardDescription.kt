package com.ttasjwi.board.system.board.domain.model

/**
 * 게시판 설명
 */
class BoardDescription internal constructor(val value: String) {

    companion object {

        fun restore(value: String): BoardDescription {
            return BoardDescription(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardDescription) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardDescription(value=$value)"
    }
}
