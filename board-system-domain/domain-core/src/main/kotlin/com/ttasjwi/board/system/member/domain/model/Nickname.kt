package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.exception.InvalidNicknameFormatException
import java.util.*

class Nickname
internal constructor(
    val value: String
) {

    companion object {

        /**
         * 한글, 영문자(대,소), 숫자만 허용
         */
        private val pattern = Regex("^[ㄱ-힣|a-z|A-Z|0-9|]+\$")

        internal const val MIN_LENGTH = 1
        internal const val MAX_LENGTH = 15
        internal const val RANDOM_NICKNAME_LENGTH = MAX_LENGTH

        fun restore(value: String): Nickname {
            return Nickname(value)
        }

        internal fun create(value: String): Nickname {
            if (value.length < MIN_LENGTH || value.length > MAX_LENGTH || !value.matches(pattern)) {
                throw InvalidNicknameFormatException(value)
            }
            return Nickname(value)
        }

        internal fun createRandom(): Nickname {
            val value = UUID.randomUUID().toString().replace("-", "")
                .substring(0, RANDOM_NICKNAME_LENGTH)
            return Nickname(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Nickname) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Nickname(value=$value)"
    }
}
