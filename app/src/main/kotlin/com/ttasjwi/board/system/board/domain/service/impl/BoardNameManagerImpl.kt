package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardNameFormatException
import com.ttasjwi.board.system.board.domain.service.BoardNameManager
import com.ttasjwi.board.system.common.annotation.component.DomainService

@DomainService
class BoardNameManagerImpl : BoardNameManager {

    companion object {

        /**
         * 영어, 숫자, 완성형 한글, 스페이스, / 로만 구성되어야함
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
    }

    override fun validate(boardName: String): Result<String> = kotlin.runCatching {
        if (boardName != boardName.trim() // 양끝 공백 허용 x
            || boardName.length < MIN_LENGTH || boardName.length > MAX_LENGTH
            || !boardName.matches(pattern)) {

            throw InvalidBoardNameFormatException()
        }
        boardName
    }
}
