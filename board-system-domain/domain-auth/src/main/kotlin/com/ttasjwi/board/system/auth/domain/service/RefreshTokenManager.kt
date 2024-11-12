package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

interface RefreshTokenManager {

    fun generate(memberId: MemberId, issuedAt: ZonedDateTime): RefreshToken
    fun parse(tokenValue: String): RefreshToken
}
