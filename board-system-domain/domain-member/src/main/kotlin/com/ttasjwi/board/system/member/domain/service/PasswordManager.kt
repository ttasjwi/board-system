package com.ttasjwi.board.system.member.domain.service

import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword

interface PasswordManager {

    fun createRawPassword(value: String): Result<RawPassword>
    fun createRandomRawPassword(): RawPassword
    fun encode(rawPassword: RawPassword): EncodedPassword
    fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean
}
