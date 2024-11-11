package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.member.domain.model.MemberId
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class ExternalRefreshTokenManagerImpl : ExternalRefreshTokenManager {

    override fun generate(
        memberId: MemberId,
        refreshTokenId: RefreshTokenId,
        issuedAt: ZonedDateTime,
        expiresAt: ZonedDateTime
    ): RefreshToken {
        TODO("Not yet implemented")
    }

    override fun parse(tokenValue: String): RefreshToken {
        TODO("Not yet implemented")
    }
}
