package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

@DomainService
internal class RefreshTokenHolderAppenderImpl : RefreshTokenHolderAppender {

    override fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, currentTime: ZonedDateTime) {
        TODO("Not yet implemented")
    }
}
