package com.ttasjwi.board.system.user.domain.util

import java.security.SecureRandom

internal class ByteKeyGenerator(
    private val secureRandom: SecureRandom,
    private val keyLength: Int,
) {

    companion object {

        internal const val DEFAULT_KEY_LENGTH = 8

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
