package com.ttasjwi.board.system.member.domain.external.fixture

import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler
import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.fixture.encodedPasswordFixture

class ExternalPasswordHandlerFixture : ExternalPasswordHandler{

    override fun encode(rawPassword: RawPassword): EncodedPassword {
        return encodedPasswordFixture(rawPassword.value)
    }

    override fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean {
        return rawPassword.value == encodedPassword.value
    }
}
