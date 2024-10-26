package com.ttasjwi.board.system.member.domain.model

/**
 * 우리 서비스에서 사용되는 역할들
 */
enum class Role {
    USER, ADMIN, ROOT, SYSTEM;

    companion object {

        fun restore(roleName: String): Role {
            return Role.valueOf(roleName)
        }
    }

    override fun toString(): String {
        return "Role(name=$name)"
    }
}
