package com.ttasjwi.board.system.user.domain.util

import com.ttasjwi.board.system.user.domain.util.ByteKeyGenerator.Companion.DEFAULT_KEY_LENGTH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-domain] ByteKeyGenerator : 임의의 바이트 배열 생성기")
class ByteKeyGeneratorTest {

    @Test
    @DisplayName("임의의 랜덤 바이트를 생성한다. 기본적으로 $DEFAULT_KEY_LENGTH 의 길이를 가진다.")
    fun test1() {
        val generator = ByteKeyGenerator.init()
        val key = generator.generateKey()
        assertThat(key.size).isEqualTo(DEFAULT_KEY_LENGTH)
    }

    @Test
    @DisplayName("key 길이를 지정해서 생성기를 생성할 수 있다.")
    fun test2() {
        val keyLength = 16
        val generator = ByteKeyGenerator.init(keyLength)
        val key = generator.generateKey()
        assertThat(key.size).isEqualTo(keyLength)
    }
}
