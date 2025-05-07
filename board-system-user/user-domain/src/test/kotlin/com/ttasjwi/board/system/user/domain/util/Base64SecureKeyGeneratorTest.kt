package com.ttasjwi.board.system.user.domain.util

import com.ttasjwi.board.system.user.domain.util.Base64SecureKeyGenerator.Companion.MIN_KEY_LENGTH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class Base64SecureKeyGeneratorTest {

    @Test
    @DisplayName("임의의 Base64 인코딩된 랜덤키를 생성한다.")
    fun test1() {
        val generator = Base64SecureKeyGenerator.init(base64Encoder = Base64.getUrlEncoder())
        val key = generator.generateKey()
        assertThat(key).isNotNull()
    }

    @Test
    @DisplayName("원본 키 크기는 최소 $MIN_KEY_LENGTH 자 이상이여야 한다.")
    fun test2() {
        val keyLength = 16
        assertThrows<IllegalArgumentException> {
            Base64SecureKeyGenerator.init(
                base64Encoder = Base64.getUrlEncoder(),
                keyLength = keyLength
            )
        }
    }
}
