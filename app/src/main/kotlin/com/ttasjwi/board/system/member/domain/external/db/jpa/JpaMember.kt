package com.ttasjwi.board.system.member.domain.external.db.jpa

import com.ttasjwi.board.system.member.domain.model.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class JpaMember(

    @Id
    @Column(name = "member_id")
    val id: Long,

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
) {

    companion object {

        internal fun from(member: Member): JpaMember {
            return JpaMember(
                id = member.id,
                email = member.email,
                password = member.password.value,
                username = member.username,
                nickname = member.nickname.value,
                role = member.role.name,
                registeredAt = member.registeredAt.toLocalDateTime(),
            )
        }
    }

    internal fun restoreDomain(): Member {
        return Member.restore(
            id = this.id,
            email = this.email,
            password = this.password,
            username = this.username,
            nickname = this.nickname,
            roleName = this.role,
            registeredAt = this.registeredAt
        )
    }
}
