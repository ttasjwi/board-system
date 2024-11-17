package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.service.AuthMemberLoader

class AuthMemberLoaderFixture(
    private var authMember: AuthMember? = null,
) : AuthMemberLoader {

    override fun loadCurrentAuthMember(): AuthMember? {
        return authMember
    }

    fun changeAuthMember(authMember: AuthMember?) {
        this.authMember = authMember
    }
}
