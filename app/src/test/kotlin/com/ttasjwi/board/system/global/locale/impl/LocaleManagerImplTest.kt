package com.ttasjwi.board.system.global.locale.impl

import com.ttasjwi.board.system.global.locale.LocaleManager
import com.ttasjwi.board.system.global.locale.impl.LocaleManagerImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

@DisplayName("LocaleManagerImpl 테스트")
class LocaleManagerImplTest {

    private lateinit var localeManager: LocaleManager

    @BeforeEach
    fun setup() {
        localeManager = LocaleManagerImpl()
    }

    @Test
    @DisplayName("LocaleContextHolder에 저장된 로케일을 반환한다")
    fun test() {
        LocaleContextHolder.setLocale(Locale.ENGLISH)

        val locale = localeManager.getCurrentLocale()
        assertThat(locale).isEqualTo(Locale.ENGLISH)
    }
}
