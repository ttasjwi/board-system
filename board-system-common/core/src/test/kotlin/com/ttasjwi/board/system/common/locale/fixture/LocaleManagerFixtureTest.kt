package com.ttasjwi.board.system.common.locale.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("LocaleManagerFixture 테스트")
class LocaleManagerFixtureTest {

    private lateinit var localeResolverFixture: LocaleResolverFixture

    @BeforeEach
    fun setup() {
        this.localeResolverFixture = LocaleResolverFixture()
    }

    @Test
    @DisplayName("기본적으로 한국어 로케일을 반환한다.")
    fun defaultLocale() {
        val locale = localeResolverFixture.getCurrentLocale()
        assertThat(locale).isEqualTo(Locale.KOREAN)
    }

    @Test
    @DisplayName("changeLocale 을 통해 로케일을 변경하고 getCurrentLocale 로 가져올 수 있다")
    fun changeAndGet() {
        localeResolverFixture.changeLocale(Locale.ENGLISH)

        val locale = localeResolverFixture.getCurrentLocale()
        assertThat(locale).isEqualTo(Locale.ENGLISH)
    }
}
