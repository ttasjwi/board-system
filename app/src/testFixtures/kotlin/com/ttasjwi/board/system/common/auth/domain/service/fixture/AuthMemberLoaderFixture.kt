package com.ttasjwi.board.system.common.auth.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.auth.domain.service.AuthMemberLoader

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
