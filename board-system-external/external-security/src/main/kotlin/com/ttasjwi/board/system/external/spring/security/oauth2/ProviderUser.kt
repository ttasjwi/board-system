package com.ttasjwi.board.system.external.spring.security.oauth2

interface ProviderUser {
    fun providerName(): String
    fun userId(): String
    fun email(): String
}
