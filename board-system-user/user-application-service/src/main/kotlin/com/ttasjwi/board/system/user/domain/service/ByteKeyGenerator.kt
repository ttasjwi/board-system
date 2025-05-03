package com.ttasjwi.board.system.user.domain.service

import java.security.SecureRandom

class ByteKeyGenerator(
    private val secureRandom: SecureRandom,
    private val keyLength: Int,
) {

    companion object {

        private const val DEFAULT_KEY_LENGTH = 8

        fun init(keyLength: Int = DEFAULT_KEY_LENGTH): ByteKeyGenerator {
            return ByteKeyGenerator(
                secureRandom = SecureRandom(),
                keyLength = keyLength
            )
        }
    }

    fun generateKey(): ByteArray {
        val bytes = ByteArray(this.keyLength)
        this.secureRandom.nextBytes(bytes)
        return bytes
    }
}
