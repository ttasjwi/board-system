package com.ttasjwi.board.system.common.auth

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
