package com.ttasjwi.board.system.global.springsecurity.oauth2.principal.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

internal abstract class CustomOAuth2User
protected constructor(
    socialServiceName: String,
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : OAuth2User, SocialServiceUserPrincipal {

    private val _socialServiceName: String = socialServiceName
    private val _authorities = authorities
    private val _attributes = attributes

    override fun socialServiceName(): String {
        return _socialServiceName
    }

    companion object {

        private val oauth2UserFactory: Map<String, (MutableCollection<out GrantedAuthority>, MutableMap<String, Any>) -> CustomOAuth2User> =
            mapOf(
                "google" to ::GoogleOAuth2User,
                "kakao" to ::KakaoOAuth2User,
                "naver" to ::NaverOAuth2User
            )

        fun from(socialServiceName: String, oauth2User: OAuth2User): CustomOAuth2User {
            return oauth2UserFactory[socialServiceName]?.invoke(oauth2User.authorities, oauth2User.attributes)
                ?: throw IllegalStateException("지원되지 않는 oauth2 서비스 제공자: $socialServiceName")
        }
    }

    override fun getName(): String {
        return socialServiceUserId()
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return _attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return _authorities
    }
}

internal class GoogleOAuth2User(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : CustomOAuth2User(
    socialServiceName = "google",
    authorities = authorities,
    attributes = attributes,
) {

    override fun socialServiceUserId(): String {
        return attributes["sub"] as String
    }

    override fun email(): String {
        return attributes["email"] as String
    }
}

internal class KakaoOAuth2User(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : CustomOAuth2User(
    socialServiceName = "kakao",
    authorities = authorities,
    attributes = attributes,
) {

    override fun socialServiceUserId(): String {
        return attributes["id"] as String
    }

    override fun email(): String {
        return (attributes["kakao_account"] as Map<*, *>)["email"] as String
    }
}

internal class NaverOAuth2User(
    authorities: MutableCollection<out GrantedAuthority>,
    attributes: MutableMap<String, Any>,
) : CustomOAuth2User(
    socialServiceName = "naver",
    authorities = authorities,
    attributes = attributes,
) {

    override fun socialServiceUserId(): String {
        return (attributes["response"] as Map<*, *>)["id"] as String
    }

    override fun email(): String {
        return (attributes["response"] as Map<*, *>)["email"] as String
    }
}
