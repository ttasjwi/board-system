package com.ttasjwi.board.system.member.domain.external

import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword

interface ExternalPasswordHandler {

    fun encode(rawPassword: RawPassword): EncodedPassword
    fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean
}
