package com.ttasjwi.board.system.member.domain.model


class RawPassword
internal constructor(
    val value: String
) {
    companion object {
        private const val RAW_PASSWORD_TO_STRING = "RawPassword(value=[SECRET])"
    }

    override fun toString(): String {
        return RAW_PASSWORD_TO_STRING
    }
}
