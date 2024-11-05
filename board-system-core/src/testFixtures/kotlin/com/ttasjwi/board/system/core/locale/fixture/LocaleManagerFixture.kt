package com.ttasjwi.board.system.core.locale.fixture

import com.ttasjwi.board.system.core.locale.LocaleManager
import java.util.*

class LocaleManagerFixture(
    private var locale: Locale = Locale.KOREAN
) : LocaleManager {

    override fun getCurrentLocale(): Locale {
        return locale
    }

    fun changeLocale(locale: Locale) {
        this.locale = locale
    }
}
