package com.ttasjwi.board.system.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "message")
class MessageProperties @ConstructorBinding constructor(
    val encoding: String,
    val errorBaseName: String,
    val generalBaseName: String,
)
