package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.exception.InvalidUsernameFormatException

/**
 * 사용자 관점 아이디
 */
class Username
internal constructor(
    val value: String
) {

    companion object {

        /**
         * 영어 소문자, 숫자, 언더바만 허용. 공백 허용 안 됨.
         */

        private val pattern = Regex("^[a-z0-9_]+\$")
        internal const val MIN_LENGTH = 4
        internal const val MAX_LENGTH = 15

        /**
         * Username 복원
         */
        fun restore(value: String): Username {
            return Username(value)
        }

        /**
         * Username 생성
         */
        internal fun create(value: String): Username {
            if (value.length < MIN_LENGTH || value.length > MAX_LENGTH || !value.matches(pattern)) {
                throw InvalidUsernameFormatException(value)
            }
            return Username(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Username) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Username(value=$value)"
    }
}
