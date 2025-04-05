package com.ttasjwi.board.system.global.auth.fixture

import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.auth.AuthMemberLoader

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
