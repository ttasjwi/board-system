package com.ttasjwi.board.system.member.domain.external

interface ExternalEmailFormatChecker {

    fun isValidFormatEmail(value: String): Boolean
}
