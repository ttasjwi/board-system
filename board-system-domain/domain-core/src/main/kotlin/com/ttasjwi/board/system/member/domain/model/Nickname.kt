package com.ttasjwi.board.system.member.domain.model

class Nickname(
    val value: String
) {

    companion object {

        fun restore(value: String): Nickname {
            return Nickname(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Nickname) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Nickname(value=$value)"
    }
}
