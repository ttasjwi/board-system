package com.ttasjwi.board.system.user.domain.service

import java.util.*

class Base64SecureKeyGenerator
private constructor(
    private val keyGenerator: ByteKeyGenerator,
    private val base64Encoder: Base64.Encoder,
) {

    companion object {
        private const val MIN_KEY_LENGTH = 32

        fun init(base64Encoder: Base64.Encoder, keyLength: Int = MIN_KEY_LENGTH): Base64SecureKeyGenerator {
            if (keyLength < MIN_KEY_LENGTH) {
                throw IllegalArgumentException("keyLength must be greater than or equal to $MIN_KEY_LENGTH")
            }
            val keyGenerator = ByteKeyGenerator.init(keyLength)
            return Base64SecureKeyGenerator(
                keyGenerator = keyGenerator,
                base64Encoder = base64Encoder,
            )
        }
    }

    fun generateKey(): String {
        val key = keyGenerator.generateKey()
        val base64EncodedKey = base64Encoder.encodeToString(key)
        return base64EncodedKey
    }
}
