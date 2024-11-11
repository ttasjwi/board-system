package com.ttasjwi.board.system.auth.domain.model

class RefreshTokenId
internal constructor(
    val value: String
) {
    companion object {
        fun restore(value: String): RefreshTokenId {
            return RefreshTokenId(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RefreshTokenId) return false
        if (value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
