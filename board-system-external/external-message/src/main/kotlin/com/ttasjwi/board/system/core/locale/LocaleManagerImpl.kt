package com.ttasjwi.board.system.core.locale

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocaleManagerImpl : LocaleManager {

    override fun getCurrentLocale(): Locale {
        return LocaleContextHolder.getLocale()
    }
}
