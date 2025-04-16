package com.ttasjwi.board.system.user.domain.policy

import com.ttasjwi.board.system.common.annotation.component.DomainPolicy
import com.ttasjwi.board.system.user.domain.exception.InvalidUsernameFormatException
import java.util.*

@DomainPolicy
internal class UsernamePolicyImpl : UsernamePolicy {

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
        return UUID
            .randomUUID()
            .toString()
            .replace(oldValue = "-", newValue = "")
            .substring(startIndex = 0, endIndex = RANDOM_USERNAME_LENGTH)
    }
}
