package com.ttasjwi.board.system.member.domain.external.fixture

import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler

class ExternalPasswordHandlerFixture : ExternalPasswordHandler {

    override fun encode(rawPassword: String): String {
        return rawPassword
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return rawPassword == encodedPassword
    }
}
