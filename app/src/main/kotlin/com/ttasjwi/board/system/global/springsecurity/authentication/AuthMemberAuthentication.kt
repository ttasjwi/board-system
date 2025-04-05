package com.ttasjwi.board.system.global.springsecurity.authentication

import com.ttasjwi.board.system.global.auth.AuthMember
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class AuthMemberAuthentication
private constructor(
    private val authMember: AuthMember
) : Authentication {

    companion object {
        fun from(authMember: AuthMember): AuthMemberAuthentication {
            return AuthMemberAuthentication(authMember)
        }
    }

    override fun getName(): String? {
        return null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${authMember.role.name}"))
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return authMember
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw IllegalStateException("cannot set this token to trusted")
    }
}
