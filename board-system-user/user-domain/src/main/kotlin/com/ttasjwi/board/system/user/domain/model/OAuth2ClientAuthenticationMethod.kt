package com.ttasjwi.board.system.user.domain.model

enum class OAuth2ClientAuthenticationMethod(val value: String) {
    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_SECRET_POST("client_secret_post"),
    HEADER("header");

    companion object {

        private fun allOAuth2ClientAuthenticationMethod(): Set<OAuth2ClientAuthenticationMethod> = entries.toSet()

        fun of(value: String): OAuth2ClientAuthenticationMethod {
            val findMethod = allOAuth2ClientAuthenticationMethod().find { it.value == value }
            if (findMethod == null) {
                throw NoSuchElementException("No OAuth2ClientAuthenticationMethod found for $value")
            }
            return findMethod
        }
    }
}
