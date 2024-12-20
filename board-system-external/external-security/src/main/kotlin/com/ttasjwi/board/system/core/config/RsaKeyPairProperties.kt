package com.ttasjwi.board.system.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "rsa-keypair")
class RsaKeyPairProperties
@ConstructorBinding constructor(
    val publicKeyPath: String,
    val privateKeyPath: String,
)
