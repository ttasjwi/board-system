package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.User
import com.ttasjwi.board.system.user.domain.policy.NicknamePolicy
import com.ttasjwi.board.system.user.domain.policy.PasswordPolicy
import com.ttasjwi.board.system.user.domain.policy.UsernamePolicy
import com.ttasjwi.board.system.user.domain.port.PasswordEncryptionPort

@DomainService
class UserCreator(
    private val passwordEncryptionPort: PasswordEncryptionPort,
    private val usernamePolicy: UsernamePolicy,
    private val nicknamePolicy: NicknamePolicy,
    private val passwordPolicy: PasswordPolicy,
) {

    private val idGenerator: IdGenerator = IdGenerator.create()

    fun create(
        email: String,
        rawPassword: String,
        username: String,
        nickname: String,
        currentTime: AppDateTime
    ): User {
        return User.create(
            userId = idGenerator.nextId(),
            email = email,
            password = passwordEncryptionPort.encode(rawPassword),
            username = username,
            nickname = nickname,
            registeredAt = currentTime,
        )
    }

    fun createRandom(email: String, currentTime: AppDateTime): User {
        return create(
            email = email,
            rawPassword = passwordPolicy.createRandomPassword(),
            username = usernamePolicy.createRandom(),
            nickname = nicknamePolicy.createRandom(),
            currentTime = currentTime
        )
    }
}
