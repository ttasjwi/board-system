package com.ttasjwi.board.system.member.domain.model

class EncodedPassword
internal constructor(
    val value: String
) {

    companion object {
        private const val ENCODED_PASSWORD_TO_STRING = "EncodedPassword(value=[SECRET])"

        fun restore(value: String): EncodedPassword {
            return EncodedPassword(value)
        }
    }

    override fun toString(): String {
        return ENCODED_PASSWORD_TO_STRING
    }
}
