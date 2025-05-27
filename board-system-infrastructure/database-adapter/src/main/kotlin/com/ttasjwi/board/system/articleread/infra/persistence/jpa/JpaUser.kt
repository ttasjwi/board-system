package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleReadJpaUser")
@Table(name = "users")
class JpaUser(

    @Id
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "username")
    val username: String,

    @Column(name = "nickname")
    val nickname: String,

    @Column(name = "role")
    val role: String,

    @Column(name = "registered_at")
    val registeredAt: LocalDateTime,
)
