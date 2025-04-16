package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.AuthUserLoader

class AuthUserLoaderFixture(
    private var authUser: AuthUser? = null,
) : AuthUserLoader {

    override fun loadCurrentAuthUser(): AuthUser? {
        return authUser
    }

    fun changeAuthUser(authUser: AuthUser?) {
        this.authUser = authUser
    }
}
