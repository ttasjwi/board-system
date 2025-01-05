package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException

/**
 * 게시판 설명
 */
class BoardDescription internal constructor(val value: String) {

    companion object {

        /**
         * 게시판 설명의 최대 글자 길이제한
         */
        internal const val MAX_LENGTH = 100

        /**
         * 게시판 설명을 생성합니다.
         */
        internal fun create(value: String): BoardDescription {
            if (value.length > MAX_LENGTH) {
                throw InvalidBoardDescriptionFormatException()
            }
            return BoardDescription(value)
        }

        fun restore(value: String): BoardDescription {
            return BoardDescription(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardDescription) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardDescription(value=$value)"
    }
}
