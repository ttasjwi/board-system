package com.ttasjwi.board.system.user.infra.crypto.config

import com.ttasjwi.board.system.user.infra.crypto.PasswordEncryptionAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class PasswordConfig {

    @Bean
    fun passwordAdapter(): PasswordEncryptionAdapter {
        return PasswordEncryptionAdapter(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}
