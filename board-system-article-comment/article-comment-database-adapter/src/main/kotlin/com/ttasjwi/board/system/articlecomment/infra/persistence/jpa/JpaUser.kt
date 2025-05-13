package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity(name = "ArticleCommentJpaUser")
@Table(name = "users")
class JpaUser(

    @Id
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "email", unique = true, nullable = false)
    var email: String,

    @Column(name = "password", length = 68, nullable = false)
    var password: String,

    @Column(name = "username", length = 15, nullable = false)
    var username: String,

    @Column(name = "nickname", length = 15, nullable = false)
    var nickname: String,

    @Column(name = "role", length = 10, nullable = false)
    var role: String,

    @Column(name = "registered_at", columnDefinition = "DATETIME", nullable = false)
    val registeredAt: LocalDateTime,
)
