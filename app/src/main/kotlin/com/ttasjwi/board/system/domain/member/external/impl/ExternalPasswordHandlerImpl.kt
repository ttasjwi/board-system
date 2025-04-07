package com.ttasjwi.board.system.domain.member.external.impl

import com.ttasjwi.board.system.domain.member.external.ExternalPasswordHandler
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ExternalPasswordHandlerImpl(
    private val passwordEncoder: PasswordEncoder,
) : ExternalPasswordHandler {

    override fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
