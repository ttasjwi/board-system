package com.ttasjwi.board.system.member.infra.persistence

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MemberRedisAdapterTestApplication

fun main(args: Array<String>) {
    runApplication<EmailVerificationPersistenceAdapter>(*args)
}
