package com.ttasjwi.board.system.member.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMemberRepository : JpaRepository<JpaMember, Long> {

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
