package com.ttasjwi.board.system.user.infra.persistence.jpa

interface AuthMemberProjection {
    fun getMemberId(): Long
    fun getRole(): String
}
