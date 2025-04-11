package com.ttasjwi.board.system.member.infra.crypto

import com.ttasjwi.board.system.member.domain.port.PasswordEncryptionPort
import org.springframework.security.crypto.password.PasswordEncoder

class PasswordEncryptionAdapter(
    private val passwordEncoder: PasswordEncoder
) : PasswordEncryptionPort {

    override fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
