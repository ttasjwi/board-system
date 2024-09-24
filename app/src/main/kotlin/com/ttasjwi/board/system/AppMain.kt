package com.ttasjwi.board.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class AppMain

fun main(args: Array<String>) {
    runApplication<AppMain>(*args)
}
