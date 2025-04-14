package com.ttasjwi.board.system.member.infra.persistence.jpa

interface AuthMemberProjection {
    fun getMemberId(): Long
    fun getRole(): String
}
