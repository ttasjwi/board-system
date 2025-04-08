package com.ttasjwi.board.system.common.websupport.message.config

import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.common.websupport.message.DefaultMessageResolver
import dev.akkinoc.util.YamlResourceBundle
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.Locale
import java.util.ResourceBundle

@Configuration
class MessageConfig(
    @Value("\${message.encoding}")
    private val messageEncoding: String,

    @Value("\${message.generalBaseName}")
    private val generalMessageBaseName: String,

    @Value("\${message.errorBaseName}")
    private val errorMessageBaseName: String,
) {

    @Bean
    fun messageResolver(): MessageResolver {
        return DefaultMessageResolver(
            generalMessageSource = generalMessageSource(),
            errorMessageSource = errorMessageSource(),
        )
    }

    @Bean(name = ["generalMessageSource"])
    fun generalMessageSource(): MessageSource {
        val messageSource = YamlMessageSource()
        messageSource.setBasename(generalMessageBaseName) // 메시지를 찾을 위치
        messageSource.setDefaultEncoding(messageEncoding) // 인코딩
        messageSource.setAlwaysUseMessageFormat(true) // 메시지 포맷 규칙 사용
        messageSource.setUseCodeAsDefaultMessage(true) // 메시지를 못 찾으면 코드 그 자체를 디폴트 메시지로 사용
        return messageSource
    }

    @Bean(name = ["errorMessageSource"])
    fun errorMessageSource(): MessageSource {
        val messageSource = YamlMessageSource()
        messageSource.setBasename(errorMessageBaseName) // 메시지를 찾을 위치
        messageSource.setDefaultEncoding(messageEncoding) // 인코딩
        messageSource.setAlwaysUseMessageFormat(true) // 메시지 포맷 규칙 사용
        messageSource.setUseCodeAsDefaultMessage(true) // 메시지를 못 찾으면 코드 그 자체를 디폴트 메시지로 사용

        return messageSource
    }

    private class YamlMessageSource : ResourceBundleMessageSource() {
        override fun doGetBundle(basename: String, locale: Locale): ResourceBundle {
            return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control)
        }
    }
}
