package com.ttasjwi.board.system.user.infra.persistence.jpa

interface AuthUserProjection {
    fun getUserId(): Long
    fun getRole(): String
}
