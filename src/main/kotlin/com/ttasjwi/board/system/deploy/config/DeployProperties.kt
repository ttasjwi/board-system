package com.ttasjwi.board.system.deploy.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "server")
class DeployProperties
@ConstructorBinding constructor(
    val profile: String,
)
