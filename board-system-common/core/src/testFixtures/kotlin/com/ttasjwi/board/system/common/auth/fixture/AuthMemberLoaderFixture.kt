package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader

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
