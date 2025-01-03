package com.ttasjwi.board.system.board.domain.model

/**
 * 게시판 슬러그
 */
class BoardSlug internal constructor(val value: String) {

    companion object {

        fun restore(value: String): BoardSlug {
            return BoardSlug(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardSlug) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardSlug(value=$value)"
    }
}
