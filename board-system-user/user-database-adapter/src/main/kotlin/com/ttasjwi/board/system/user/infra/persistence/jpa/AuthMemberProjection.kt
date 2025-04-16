package com.ttasjwi.board.system.user.infra.persistence.jpa

interface AuthMemberProjection {
    fun getUserId(): Long
    fun getRole(): String
}
