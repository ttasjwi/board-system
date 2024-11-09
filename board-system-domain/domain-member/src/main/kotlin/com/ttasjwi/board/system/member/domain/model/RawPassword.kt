package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.exception.InvalidPasswordFormatException

class RawPassword
internal constructor(
    val value: String
) {
    companion object {
        private const val RAW_PASSWORD_TO_STRING = "RawPassword(value=[SECRET])"
        internal const val MIN_LENGTH = 4
        internal const val MAX_LENGTH = 32

        internal fun create(value: String): RawPassword {
            if (value.length < MIN_LENGTH || value.length > MAX_LENGTH) {
                throw InvalidPasswordFormatException()
            }
            return RawPassword(value)
        }
    }

    override fun toString(): String {
        return RAW_PASSWORD_TO_STRING
    }

}
