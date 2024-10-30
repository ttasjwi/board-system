package com.ttasjwi.board.system.member.domain.external.fixture

import com.ttasjwi.board.system.member.domain.external.ExternalEmailFormatChecker

internal class ExternalEmailFormatCheckerFixture : ExternalEmailFormatChecker {

    companion object {
        const val INVALID_FORMAT_EMAIL = "wrong!@gmail.com"
    }

    override fun isValidFormatEmail(value: String): Boolean {
        return value != INVALID_FORMAT_EMAIL
    }
}
