package com.ttasjwi.board.system.core.config

import dev.akkinoc.util.YamlResourceBundle
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*

@Configuration
class MessageConfig(
    private val messageProperties: MessageProperties,
) {

    @Bean(name = ["errorMessageSource"])
    fun errorMessageSource(): MessageSource {
        val messageSource = YamlMessageSource()
        messageSource.setBasename(messageProperties.errorBaseName) // 메시지를 찾을 위치
        messageSource.setDefaultEncoding(messageProperties.encoding) // 인코딩
        messageSource.setAlwaysUseMessageFormat(true) // 메시지 포맷 규칙 사용
        messageSource.setUseCodeAsDefaultMessage(true) // 메시지를 못 찾으면 코드 그 자체를 디폴트 메시지로 사용

        return messageSource
    }


    @Bean(name = ["generalMessageSource"])
    fun generalMessageSource(): MessageSource {
        val messageSource = YamlMessageSource()
        messageSource.setBasename(messageProperties.generalBaseName) // 메시지를 찾을 위치
        messageSource.setDefaultEncoding(messageProperties.encoding) // 인코딩
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
