package com.ttasjwi.board.system.common.locale.impl

import com.ttasjwi.board.system.common.locale.LocaleManager
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocaleManagerImpl : LocaleManager {

    override fun getCurrentLocale(): Locale {
        return LocaleContextHolder.getLocale()
    }
}
