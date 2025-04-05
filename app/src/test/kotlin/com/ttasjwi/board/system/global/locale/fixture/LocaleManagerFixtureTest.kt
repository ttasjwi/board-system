package com.ttasjwi.board.system.global.locale.fixture

import com.ttasjwi.board.system.global.locale.fixture.LocaleManagerFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("LocaleManagerFixture 테스트")
class LocaleManagerFixtureTest {

    private lateinit var localeManager: LocaleManagerFixture

    @BeforeEach
    fun setup() {
        this.localeManager = LocaleManagerFixture()
    }

    @Test
    @DisplayName("기본적으로 한국어 로케일을 반환한다.")
    fun defaultLocale() {
        val locale = localeManager.getCurrentLocale()

        assertThat(locale).isEqualTo(Locale.KOREAN)
    }

    @Test
    @DisplayName("chanageLocale 을 통해 로케일을 변경하고 getCurrentLocale 로 가져올 수 있다")
    fun changeAndGet() {
        localeManager.changeLocale(Locale.ENGLISH)

        val locale = localeManager.getCurrentLocale()
        assertThat(locale).isEqualTo(Locale.ENGLISH)
    }
}
