package com.ttasjwi.board.system.member.domain.external.db.jpa

import com.ttasjwi.board.system.member.domain.model.Member
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name= "member")
class JpaMember (

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "username")
    var username: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "role")
    var role: String,

    @Column(name = "registered_at")
    val registeredAt: ZonedDateTime,
) {

    companion object {

        fun from(member: Member): JpaMember {
            return JpaMember(
                id = member.id?.value,
                email = member.email.value,
                password = member.password.value,
                username = member.username.value,
                nickname = member.nickname.value,
                role = member.role.name,
                registeredAt = member.registeredAt,
            )
        }
    }

    fun restoreDomain(): Member {
        return Member.restore(
            id = this.id!!,
            email = this.email,
            password = this.password,
            username = this.username,
            nickname = this.nickname,
            roleName = this.role,
            registeredAt = this.registeredAt
        )
    }
}
