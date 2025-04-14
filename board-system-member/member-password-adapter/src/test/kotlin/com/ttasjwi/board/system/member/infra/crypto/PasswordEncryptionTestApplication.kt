package com.ttasjwi.board.system.member.infra.crypto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PasswordEncryptionTestApplication

fun main(args: Array<String>) {
    runApplication<PasswordEncryptionTestApplication>(*args)
}
