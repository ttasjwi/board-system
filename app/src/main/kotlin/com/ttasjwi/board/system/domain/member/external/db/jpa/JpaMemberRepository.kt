package com.ttasjwi.board.system.domain.member.external.db.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaMemberRepository : JpaRepository<JpaMember, Long> {

    @Query("SELECT m FROM JpaMember m WHERE m.email = :email")
    fun findByEmailOrNull(@Param("email") email: String): JpaMember?
    fun existsByEmail(email: String): Boolean

    @Query("SELECT m FROM JpaMember m WHERE m.username = :username")
    fun findByUsernameOrNull(@Param("username") username: String): JpaMember?
    fun existsByUsername(username: String): Boolean


    @Query("SELECT m FROM JpaMember m WHERE m.nickname = :nickname")
    fun findByNicknameOrNull(@Param("nickname") nickname: String): JpaMember?
    fun existsByNickname(nickname: String): Boolean
}
