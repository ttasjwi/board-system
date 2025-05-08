package com.ttasjwi.board.system.user.domain.test.support

import com.ttasjwi.board.system.common.auth.fixture.AccessTokenPortFixture
import com.ttasjwi.board.system.common.auth.fixture.RefreshTokenPortFixture
import com.ttasjwi.board.system.common.event.fixture.EventPublishPortFixture
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.user.domain.*
import com.ttasjwi.board.system.user.domain.mapper.*
import com.ttasjwi.board.system.user.domain.policy.fixture.NicknamePolicyFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.PasswordPolicyFixture
import com.ttasjwi.board.system.user.domain.policy.fixture.UsernamePolicyFixture
import com.ttasjwi.board.system.user.domain.port.fixture.*
import com.ttasjwi.board.system.user.domain.processor.*
import com.ttasjwi.board.system.user.domain.service.OAuth2UserPrincipalLoader
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.service.UserCreator

internal class TestContainer private constructor() {

    companion object {

        internal fun create(): TestContainer {
            return TestContainer()
        }
    }

    val timeManagerFixture: TimeManagerFixture by lazy { TimeManagerFixture() }
    val localeResolverFixture: LocaleResolverFixture by lazy { LocaleResolverFixture() }
    private val messageResolverFixture: MessageResolverFixture by lazy { MessageResolverFixture() }
    private val nicknamePolicyFixture: NicknamePolicyFixture by lazy { NicknamePolicyFixture() }
    private val usernamePolicyFixture: UsernamePolicyFixture by lazy { UsernamePolicyFixture() }
    private val passwordPolicyFixture: PasswordPolicyFixture by lazy { PasswordPolicyFixture() }


    // ports
    private val eventPublishPortFixture: EventPublishPortFixture by lazy { EventPublishPortFixture() }
    private val accessTokenPortFixture: AccessTokenPortFixture by lazy { AccessTokenPortFixture() }
    val refreshTokenPortFixture: RefreshTokenPortFixture by lazy { RefreshTokenPortFixture() }
    val emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture by lazy { EmailVerificationPersistencePortFixture() }
    val userPersistencePortFixture: UserPersistencePortFixture by lazy { UserPersistencePortFixture() }

    val oAuth2AuthorizationRequestPersistencePortFixture: OAuth2AuthorizationRequestPersistencePortFixture by lazy { OAuth2AuthorizationRequestPersistencePortFixture() }
    private val oAuth2ClientRegistrationPersistencePortFixture: OAuth2ClientRegistrationPersistencePortFixture by lazy { OAuth2ClientRegistrationPersistencePortFixture() }

    val socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture by lazy { SocialConnectionPersistencePortFixture() }
    private val emailFormatValidatePortFixture: EmailFormatValidatePortFixture by lazy { EmailFormatValidatePortFixture() }
    val passwordEncryptionPortFixture: PasswordEncryptionPortFixture by lazy { PasswordEncryptionPortFixture() }
    val userRefreshTokenIdListPersistencePortFixture: UserRefreshTokenIdListPersistencePortFixture by lazy { UserRefreshTokenIdListPersistencePortFixture() }
    val refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture by lazy { RefreshTokenIdPersistencePortFixture() }
    val oAuth2AccessTokenClientPortFixture: OAuth2AccessTokenClientPortFixture by lazy { OAuth2AccessTokenClientPortFixture() }
    val oAuth2UserPrincipalClientPortFixture: OAuth2UserPrincipalClientPortFixture by lazy { OAuth2UserPrincipalClientPortFixture() }
    val oidcOAuth2UserPrincipalPortFixture: OidcOAuth2UserPrincipalPortFixture by lazy { OidcOAuth2UserPrincipalPortFixture() }


    // service
    val userCreator: UserCreator by lazy {
        UserCreator(
            passwordEncryptionPort = passwordEncryptionPortFixture,
            usernamePolicy = usernamePolicyFixture,
            nicknamePolicy = nicknamePolicyFixture,
            passwordPolicy = passwordPolicyFixture,
        )
    }

    val refreshTokenHandler: RefreshTokenHandler by lazy {
        RefreshTokenHandler(
            refreshTokenGeneratePort = refreshTokenPortFixture,
            refreshTokenParsePort = refreshTokenPortFixture,
            userRefreshTokenIdListPersistencePort = userRefreshTokenIdListPersistencePortFixture,
            refreshTokenIdPersistencePort = refreshTokenIdPersistencePortFixture
        )
    }

    val oAuth2UserPrincipalLoader: OAuth2UserPrincipalLoader by lazy {
        OAuth2UserPrincipalLoader(
            oAuth2AccessTokenClientPort = oAuth2AccessTokenClientPortFixture,
            oAuth2UserPrincipalClientPort = oAuth2UserPrincipalClientPortFixture,
            oidcOAuth2UserPrincipalPort = oidcOAuth2UserPrincipalPortFixture
        )
    }

    // queryMapper & commandMapper
    val emailAvailableQueryMapper: EmailAvailableQueryMapper by lazy {
        EmailAvailableQueryMapper(localeResolverFixture)
    }

    val nicknameAvailableQueryMapper: NicknameAvailableQueryMapper by lazy {
        NicknameAvailableQueryMapper(localeResolverFixture)
    }

    val usernameAvailableQueryMapper: UsernameAvailableQueryMapper by lazy {
        UsernameAvailableQueryMapper(localeResolverFixture)
    }

    val emailVerificationStartCommandMapper: EmailVerificationStartCommandMapper by lazy {
        EmailVerificationStartCommandMapper(
            emailFormatValidatePort = emailFormatValidatePortFixture,
            localeResolver = localeResolverFixture,
            timeManager = timeManagerFixture
        )
    }

    val emailVerificationCommandMapper: EmailVerificationCommandMapper by lazy {
        EmailVerificationCommandMapper(
            emailFormatValidatePort = emailFormatValidatePortFixture,
            timeManager = timeManagerFixture
        )
    }

    val registerUserCommandMapper: RegisterUserCommandMapper by lazy {
        RegisterUserCommandMapper(
            emailFormatValidatePort = emailFormatValidatePortFixture,
            usernamePolicy = usernamePolicyFixture,
            nicknamePolicy = nicknamePolicyFixture,
            passwordPolicy = passwordPolicyFixture,
            timeManager = timeManagerFixture
        )
    }

    val loginCommandMapper: LoginCommandMapper by lazy {
        LoginCommandMapper(
            timeManager = timeManagerFixture,
        )
    }

    val socialServiceAuthorizationCommandMapper: SocialServiceAuthorizationCommandMapper by lazy {
        SocialServiceAuthorizationCommandMapper(
            timeManager = timeManagerFixture,
        )
    }

    val socialLoginCommandMapper: SocialLoginCommandMapper by lazy {
        SocialLoginCommandMapper(
            timeManager = timeManagerFixture,
        )
    }

    val tokenRefreshCommandMapper: TokenRefreshCommandMapper by lazy {
        TokenRefreshCommandMapper(
            timeManager = timeManagerFixture,
        )
    }

    // processor
    val emailAvailableProcessor: EmailAvailableProcessor by lazy {
        EmailAvailableProcessor(
            emailFormatValidatePort = emailFormatValidatePortFixture,
            userPersistencePort = userPersistencePortFixture,
            messageResolver = messageResolverFixture
        )
    }

    val usernameAvailableProcessor: UsernameAvailableProcessor by lazy {
        UsernameAvailableProcessor(
            usernamePolicy = usernamePolicyFixture,
            userPersistencePort = userPersistencePortFixture,
            messageResolver = messageResolverFixture,
        )
    }

    val nicknameAvailableProcessor: NicknameAvailableProcessor by lazy {
        NicknameAvailableProcessor(
            nicknamePolicy = nicknamePolicyFixture,
            userPersistencePort = userPersistencePortFixture,
            messageResolver = messageResolverFixture,
        )
    }

    val emailVerificationStartProcessor: EmailVerificationStartProcessor by lazy {
        EmailVerificationStartProcessor(
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
            eventPublishPort = eventPublishPortFixture,
        )
    }

    val emailVerificationProcessor: EmailVerificationProcessor by lazy {
        EmailVerificationProcessor(
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
        )
    }

    val registerUserProcessor: RegisterUserProcessor by lazy {
        RegisterUserProcessor(
            userPersistencePort = userPersistencePortFixture,
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
            userCreator = userCreator,
        )
    }

    val loginProcessor: LoginProcessor by lazy {
        LoginProcessor(
            userPersistencePort = userPersistencePortFixture,
            passwordEncryptionPort = passwordEncryptionPortFixture,
            accessTokenGeneratePort = accessTokenPortFixture,
            refreshTokenHandler = refreshTokenHandler,
        )
    }

    val socialServiceAuthorizationProcessor: SocialServiceAuthorizationProcessor by lazy {
        SocialServiceAuthorizationProcessor(
            oAuth2ClientRegistrationPersistencePort = oAuth2ClientRegistrationPersistencePortFixture,
            oAuth2AuthorizationRequestPersistencePort = oAuth2AuthorizationRequestPersistencePortFixture,
        )
    }

    val socialLoginProcessor: SocialLoginProcessor by lazy {
        SocialLoginProcessor(
            oAuth2AuthorizationRequestPersistencePort = oAuth2AuthorizationRequestPersistencePortFixture,
            oAuth2ClientRegistrationPersistencePort = oAuth2ClientRegistrationPersistencePortFixture,
            oAuth2UserPrincipalLoader = oAuth2UserPrincipalLoader,
            userPersistencePort = userPersistencePortFixture,
            socialConnectionPersistencePort = socialConnectionPersistencePortFixture,
            userCreator = userCreator,
            accessTokenGeneratePort = accessTokenPortFixture,
            refreshTokenHandler = refreshTokenHandler
        )
    }

    val tokenRefreshProcessor: TokenRefreshProcessor by lazy {
        TokenRefreshProcessor(
            userPersistencePort = userPersistencePortFixture,
            accessTokenGeneratePort = accessTokenPortFixture,
            refreshTokenHandler = refreshTokenHandler
        )
    }

    // usecase

    val emailAvailableUseCase: EmailAvailableUseCase by lazy {
        EmailAvailableUseCaseImpl(
            queryMapper = emailAvailableQueryMapper,
            processor = emailAvailableProcessor,
        )
    }

    val nicknameAvailableUseCase: NicknameAvailableUseCase by lazy {
        NicknameAvailableUseCaseImpl(
            queryMapper = nicknameAvailableQueryMapper,
            processor = nicknameAvailableProcessor,
        )
    }

    val usernameAvailableUseCase: UsernameAvailableUseCase by lazy {
        UsernameAvailableUseCaseImpl(
            queryMapper = usernameAvailableQueryMapper,
            processor = usernameAvailableProcessor,
        )
    }

    val emailVerificationStartUseCase: EmailVerificationStartUseCase by lazy {
        EmailVerificationStartUseCaseImpl(
            commandMapper = emailVerificationStartCommandMapper,
            processor = emailVerificationStartProcessor
        )
    }

    val emailVerificationUseCase: EmailVerificationUseCase by lazy {
        EmailVerificationUseCaseImpl(
            commandMapper = emailVerificationCommandMapper,
            processor = emailVerificationProcessor,
        )
    }

    val registerUserUseCase: RegisterUserUseCase by lazy {
        RegisterUserUseCaseImpl(
            commandMapper = registerUserCommandMapper,
            processor = registerUserProcessor,
        )
    }

    val loginUseCase: LoginUseCaseImpl by lazy {
        LoginUseCaseImpl(
            commandMapper = loginCommandMapper,
            processor = loginProcessor,
        )
    }

    val socialServiceAuthorizationUseCase: SocialServiceAuthorizationUseCase by lazy {
        SocialServiceAuthorizationUseCaseImpl(
            commandMapper = socialServiceAuthorizationCommandMapper,
            processor = socialServiceAuthorizationProcessor,
        )
    }

    val socialLoginUseCase: SocialLoginUseCase by lazy {
        SocialLoginUseCaseImpl(
            commandMapper = socialLoginCommandMapper,
            processor = socialLoginProcessor,
        )
    }

    val tokenRefreshUseCase: TokenRefreshUseCase by lazy {
        TokenRefreshUseCaseImpl(
            commandMapper = tokenRefreshCommandMapper,
            processor = tokenRefreshProcessor,
        )
    }
}
