package com.ttasjwi.board.system.auth.domain.model

import java.util.*

class RefreshTokenId
internal constructor(
    val value: String
) {
    companion object {

        internal const val REFRESH_TOKEN_ID_LENGTH = 6

        internal fun create(): RefreshTokenId {
            return RefreshTokenId(
                UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, REFRESH_TOKEN_ID_LENGTH)
            )
        }

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
