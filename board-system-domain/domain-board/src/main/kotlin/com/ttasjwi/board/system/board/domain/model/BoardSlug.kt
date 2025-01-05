package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardSlugFormatException

/**
 * 게시판 슬러그
 */
class BoardSlug internal constructor(val value: String) {

    companion object {

        /**
         * 영어 소문자 또는 숫자
         */
        private val pattern = Regex("^[a-z0-9]+\$")

        /**
         * 최소 길이 제한
         */
        internal const val MIN_LENGTH = 4

        /**
         * 최대 길이 제한
         */
        internal const val MAX_LENGTH = 20

        fun restore(value: String): BoardSlug {
            return BoardSlug(value)
        }

        internal fun create(value: String): BoardSlug {
            if (value.length < MIN_LENGTH || value.length > MAX_LENGTH || !pattern.matches(value)) {
                throw InvalidBoardSlugFormatException()
            }
            return BoardSlug(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardSlug) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardSlug(value=$value)"
    }
}
