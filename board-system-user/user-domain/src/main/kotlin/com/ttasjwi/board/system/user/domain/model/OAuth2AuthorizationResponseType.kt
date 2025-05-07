package com.ttasjwi.board.system.user.domain.model

enum class OAuth2AuthorizationResponseType (val value: String) {

    CODE("code");

    companion object {

        private fun allOAuth2AuthorizationResponseType(): Set<OAuth2AuthorizationResponseType> = OAuth2AuthorizationResponseType.entries.toSet()

        fun restore(value: String): OAuth2AuthorizationResponseType {
            val findType = allOAuth2AuthorizationResponseType().find { it.value == value.lowercase() }
            if (findType == null) {
                throw NoSuchElementException("No OAuth2AuthorizationResponseType found for $value")
            }
            return findType
        }
    }
}
