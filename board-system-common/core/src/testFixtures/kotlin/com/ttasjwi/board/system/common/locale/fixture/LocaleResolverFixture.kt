package com.ttasjwi.board.system.common.locale.fixture

import com.ttasjwi.board.system.common.locale.LocaleResolver
import java.util.*

class LocaleResolverFixture(
    private var locale: Locale = Locale.KOREAN
) : LocaleResolver {

    override fun getCurrentLocale(): Locale {
        return locale
    }

    fun changeLocale(locale: Locale) {
        this.locale = locale
    }
}
