package com.ttasjwi.board.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MessageTestApplication

fun main(args: Array<String>) {
    runApplication<MessageTestApplication>(*args)
}
