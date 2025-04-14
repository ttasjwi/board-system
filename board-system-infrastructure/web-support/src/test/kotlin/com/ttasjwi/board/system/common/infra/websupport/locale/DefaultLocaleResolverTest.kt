package com.ttasjwi.board.system.common.infra.websupport.locale

import com.ttasjwi.board.system.common.locale.LocaleResolver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

@DisplayName("LocaleManagerImpl 테스트")
class DefaultLocaleResolverTest {

    private lateinit var localeResolver: LocaleResolver

    @BeforeEach
    fun setup() {
        localeResolver = DefaultLocaleResolver()
    }

    @Test
    @DisplayName("LocaleContextHolder 에 저장된 로케일을 반환한다")
    fun test() {
        resolverTest(Locale.ENGLISH)
        resolverTest(Locale.KOREAN)
        resolverTest(Locale.CHINESE)
        resolverTest(Locale.FRANCE)
        resolverTest(Locale.GERMANY)
        resolverTest(Locale.ITALIAN)
        resolverTest(Locale.JAPAN)
    }

    private fun resolverTest(locale: Locale) {
        LocaleContextHolder.setLocale(locale)
        val resolvedLocale = localeResolver.getCurrentLocale()
        assertThat(resolvedLocale).isEqualTo(locale)
    }
}
