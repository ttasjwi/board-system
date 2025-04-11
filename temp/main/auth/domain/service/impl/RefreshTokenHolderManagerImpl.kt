package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.auth.AuthMember

@DomainService
internal class RefreshTokenHolderManagerImpl : RefreshTokenHolderManager {

    override fun createRefreshTokenHolder(authMember: AuthMember): RefreshTokenHolder {
        return RefreshTokenHolder.create(authMember)
    }

    override fun addNewRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        refreshToken: RefreshToken
    ): RefreshTokenHolder {
        return refreshTokenHolder.addNewRefreshToken(refreshToken)
    }

    override fun changeRefreshToken(
        refreshTokenHolder: RefreshTokenHolder,
        previousToken: RefreshToken,
        newToken: RefreshToken
    ): RefreshTokenHolder {
        return refreshTokenHolder.changeRefreshToken(previousToken, newToken)
    }
}
