package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.global.annotation.DomainService
import com.ttasjwi.board.system.member.domain.exception.InvalidNicknameFormatException
import com.ttasjwi.board.system.member.domain.service.NicknameManager
import java.util.*

@DomainService
internal class NicknameManagerImpl : NicknameManager {

    companion object {
        /**
         * 한글, 영문자(대,소), 숫자만 허용
         */
        private val pattern = Regex("^[ㄱ-힣|a-z|A-Z|0-9|]+\$")

        internal const val MIN_LENGTH = 1
        internal const val MAX_LENGTH = 15
        internal const val RANDOM_NICKNAME_LENGTH = MAX_LENGTH
    }

    override fun validate(nickname: String): Result<String> = kotlin.runCatching {
        if (nickname.length < MIN_LENGTH || nickname.length > MAX_LENGTH || !nickname.matches(pattern)) {
            throw InvalidNicknameFormatException(nickname)
        }
        nickname
    }

    override fun createRandom(): String {
        return UUID.randomUUID().toString().replace("-", "")
            .substring(0, RANDOM_NICKNAME_LENGTH)
    }
}
