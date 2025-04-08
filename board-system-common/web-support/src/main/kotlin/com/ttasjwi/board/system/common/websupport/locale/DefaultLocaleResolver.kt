package com.ttasjwi.board.system.common.websupport.locale

import com.ttasjwi.board.system.common.locale.LocaleResolver
import org.springframework.context.i18n.LocaleContextHolder
import java.util.*

class DefaultLocaleResolver : LocaleResolver {

    override fun getCurrentLocale(): Locale {
        return LocaleContextHolder.getLocale()
    }
}
