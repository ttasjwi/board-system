package com.ttasjwi.board.system.global.locale.impl

import com.ttasjwi.board.system.global.locale.LocaleManager
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

class LocaleManagerImpl : LocaleManager {

    override fun getCurrentLocale(): Locale {
        return LocaleContextHolder.getLocale()
    }
}
