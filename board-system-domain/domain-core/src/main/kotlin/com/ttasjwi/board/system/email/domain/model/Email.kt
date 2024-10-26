package com.ttasjwi.board.system.email.domain.model

class Email(
    val value: String
) {

    companion object {
        /**
         * 기본 Email 객체 복원
         */
        fun restore(value: String): Email {
            return Email(value)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Email) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Email(value=$value)"
    }
}
