package com.ttasjwi.board.system.domain.member.external

class ExternalPasswordHandlerFixture : ExternalPasswordHandler {

    override fun encode(rawPassword: String): String {
        return rawPassword
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return rawPassword == encodedPassword
    }
}
