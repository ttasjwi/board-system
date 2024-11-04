package com.ttasjwi.board.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DbTestApplication

fun main(args: Array<String>) {
    runApplication<DbTestApplication>(*args)
}
