package com.ttasjwi.board.system.board.domain.model

class BoardName internal constructor(val value: String) {

    companion object {

        fun restore(value: String): BoardName {
            return BoardName(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardName) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardName(value=$value)"
    }
}
