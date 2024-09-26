package com.ttasjwi.board.system.deploy.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("server")
class ServerProperties
@ConstructorBinding
constructor(
    val env: String,
    val address: String,
    val port: String,
    val name: String,
)
