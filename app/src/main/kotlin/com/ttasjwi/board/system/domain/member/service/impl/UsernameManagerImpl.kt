package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.domain.member.exception.InvalidUsernameFormatException
import com.ttasjwi.board.system.domain.member.service.UsernameManager
import com.ttasjwi.board.system.global.annotation.DomainService
import java.util.*

@DomainService
internal class UsernameManagerImpl : UsernameManager {

    companion object {
        /**
         * 영어 소문자, 숫자, 언더바만 허용. 공백 허용 안 됨.
         */

        private val pattern = Regex("^[a-z0-9_]+\$")
        internal const val MIN_LENGTH = 4
        internal const val MAX_LENGTH = 15
        internal const val RANDOM_USERNAME_LENGTH = MAX_LENGTH
    }


    override fun validate(username: String): Result<String> = runCatching {
        if (username.length < MIN_LENGTH || username.length > MAX_LENGTH || !username.matches(pattern)) {
            throw InvalidUsernameFormatException(username)
        }
        username
    }

    override fun createRandom(): String {
        return UUID.randomUUID().toString()
            .replace("-", "")
            .substring(0, RANDOM_USERNAME_LENGTH)
    }
}
