package com.ttasjwi.board.system.common.auth.infra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JwtTestApplication

fun main(args: Array<String>) {
    runApplication<JwtTestApplication>(*args)
}
