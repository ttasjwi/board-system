package com.ttasjwi.board.system.external.spring.security.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

internal abstract class CustomOAuth2User
protected constructor(
    private val _providerName: String,
    private val _authorities: MutableCollection<out GrantedAuthority>,
    private val _attributes: MutableMap<String, Any>,
) : OAuth2User, ProviderUser {

    override fun providerName(): String {
        return _providerName
    }

    companion object {

        private val oauth2UserFactory: Map<String, (OAuth2User, String) -> CustomOAuth2User> = mapOf(
            "google" to ::GoogleOAuth2User,
            "kakao" to ::KakaoOAuth2User,
            "naver" to ::NaverOAuth2User
        )

        fun of(oauth2User: OAuth2User, providerName: String): CustomOAuth2User {
            return oauth2UserFactory[providerName]?.invoke(oauth2User, providerName)
                ?: throw IllegalStateException("지원되지 않는 oauth2 provider: $providerName")
        }
    }

    override fun getName(): String {
        return userId()
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return _attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return _authorities
    }
}

internal class GoogleOAuth2User(
    oauth2User: OAuth2User,
    providerName: String,
) : CustomOAuth2User(
    _providerName = providerName,
    _authorities = oauth2User.authorities,
    _attributes = oauth2User.attributes,
) {

    override fun userId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}

internal class KakaoOAuth2User(
    oauth2User: OAuth2User,
    providerName: String,
) : CustomOAuth2User(
    _providerName = providerName,
    _authorities = oauth2User.authorities,
    _attributes = oauth2User.attributes,
) {
    override fun userId(): String {
        return attributes["id"] as String
    }

    override fun email(): String {
        return (attributes["kakao_account"] as Map<*, *>)["email"] as String
    }
}

internal class NaverOAuth2User(
    oauth2User: OAuth2User,
    providerName: String,
) : CustomOAuth2User(
    _providerName = providerName,
    _authorities = oauth2User.authorities,
    _attributes = oauth2User.attributes,
) {

    override fun userId(): String {
        return (attributes["response"] as Map<*, *>)["id"] as String
    }

    override fun email(): String {
        return (attributes["response"] as Map<*, *>)["email"] as String
    }
}
