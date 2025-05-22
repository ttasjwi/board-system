package com.ttasjwi.board.system

import com.ttasjwi.board.system.user.infra.persistence.EmailVerificationPersistenceAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserRedisAdapterTestApplication

fun main(args: Array<String>) {
    runApplication<EmailVerificationPersistenceAdapter>(*args)
}
