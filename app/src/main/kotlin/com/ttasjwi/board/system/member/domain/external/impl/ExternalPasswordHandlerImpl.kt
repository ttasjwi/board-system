package com.ttasjwi.board.system.member.domain.external.impl

import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler
import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ExternalPasswordHandlerImpl(
    private val passwordEncoder: PasswordEncoder,
) : ExternalPasswordHandler {

    override fun encode(rawPassword: RawPassword): EncodedPassword {
        val encodedPasswordValue = passwordEncoder.encode(rawPassword.value)
        return EncodedPassword.restore(encodedPasswordValue)
    }

    override fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean {
        return passwordEncoder.matches(rawPassword.value, encodedPassword.value)
    }
}
