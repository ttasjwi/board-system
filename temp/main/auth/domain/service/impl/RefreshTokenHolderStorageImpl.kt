package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderFinder
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.time.AppDateTime

@DomainService
internal class RefreshTokenHolderStorageImpl(
    private val externalRefreshTokenHolderAppender: ExternalRefreshTokenHolderAppender,
    private val externalRefreshTokenHolderFinder: ExternalRefreshTokenHolderFinder,
) : RefreshTokenHolderAppender, RefreshTokenHolderFinder {

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: AppDateTime) {
        val expiresAt = refreshTokenHolder.expiresAt(currentTime)
        externalRefreshTokenHolderAppender.append(memberId, refreshTokenHolder, expiresAt)
    }

    override fun removeByMemberId(memberId: Long) {
        externalRefreshTokenHolderAppender.removeByMemberId(memberId)
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        return externalRefreshTokenHolderFinder.findByMemberIdOrNull(memberId)
    }
}
