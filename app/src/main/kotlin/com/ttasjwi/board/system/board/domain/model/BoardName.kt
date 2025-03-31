package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardNameFormatException

class BoardName
internal constructor(
    val value: String
) {

    companion object {

        /**
         * 양끝 공백을 제거했을 때 영어, 숫자, 완성형 한글, 스페이스, / 로만 구성되어야함
         */
        private val pattern = Regex("^[a-zA-Z0-9가-힣/ ]+$")

        /**
         * 최소 2자
         */
        internal const val MIN_LENGTH = 2

        /**
         * 최대 16자
         */
        internal const val MAX_LENGTH = 16

        fun restore(value: String): BoardName {
            return BoardName(value)
        }

        internal fun create(value: String): BoardName {
            val trimmedValue = value.trim()
            if (trimmedValue.length < MIN_LENGTH || trimmedValue.length > MAX_LENGTH || !trimmedValue.matches(pattern)) {
                throw InvalidBoardNameFormatException()
            }
            return BoardName(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardName) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardName(value=$value)"
    }
}
