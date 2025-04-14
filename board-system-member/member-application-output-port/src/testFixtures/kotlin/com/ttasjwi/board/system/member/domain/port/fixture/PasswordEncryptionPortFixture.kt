package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.member.domain.port.PasswordEncryptionPort

class PasswordEncryptionPortFixture : PasswordEncryptionPort {

    override fun encode(rawPassword: String): String {
        return rawPassword
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return rawPassword == encodedPassword
    }
}
