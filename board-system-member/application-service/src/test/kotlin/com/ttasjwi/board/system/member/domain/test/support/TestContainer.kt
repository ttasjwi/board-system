package com.ttasjwi.board.system.member.domain.test.support

import com.ttasjwi.board.system.common.auth.fixture.AccessTokenPortFixture
import com.ttasjwi.board.system.common.auth.fixture.RefreshTokenPortFixture
import com.ttasjwi.board.system.common.event.fixture.EventPublishPortFixture
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.member.domain.*
import com.ttasjwi.board.system.member.domain.mapper.*
import com.ttasjwi.board.system.member.domain.policy.fixture.NicknamePolicyFixture
import com.ttasjwi.board.system.member.domain.policy.fixture.PasswordPolicyFixture
import com.ttasjwi.board.system.member.domain.policy.fixture.UsernamePolicyFixture
import com.ttasjwi.board.system.member.domain.port.fixture.*
import com.ttasjwi.board.system.member.domain.processor.*
import com.ttasjwi.board.system.member.domain.service.RefreshTokenHandler

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
    val memberPersistencePortFixture: MemberPersistencePortFixture by lazy { MemberPersistencePortFixture() }
    private val emailFormatValidatePortFixture: EmailFormatValidatePortFixture by lazy { EmailFormatValidatePortFixture() }
    val passwordEncryptionPortFixture: PasswordEncryptionPortFixture by lazy { PasswordEncryptionPortFixture() }
    val memberRefreshTokenIdListPersistencePortFixture: MemberRefreshTokenIdListPersistencePortFixture by lazy { MemberRefreshTokenIdListPersistencePortFixture() }
    val refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture by lazy { RefreshTokenIdPersistencePortFixture() }


    // service
    val refreshTokenHandler: RefreshTokenHandler by lazy {
        RefreshTokenHandler(
            refreshTokenGeneratePort = refreshTokenPortFixture,
            refreshTokenParsePort = refreshTokenPortFixture,
            memberRefreshTokenIdListPersistencePort = memberRefreshTokenIdListPersistencePortFixture,
            refreshTokenIdPersistencePort = refreshTokenIdPersistencePortFixture
        )
    }

    // commandMapper
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

    val registerMemberCommandMapper: RegisterMemberCommandMapper by lazy {
        RegisterMemberCommandMapper(
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

    val tokenRefreshCommandMapper: TokenRefreshCommandMapper by lazy {
        TokenRefreshCommandMapper(
            timeManager = timeManagerFixture,
        )
    }

    // processor
    val emailAvailableProcessor: EmailAvailableProcessor by lazy {
        EmailAvailableProcessor(
            emailFormatValidatePort = emailFormatValidatePortFixture,
            memberPersistencePort = memberPersistencePortFixture,
            messageResolver = messageResolverFixture
        )
    }

    val usernameAvailableProcessor: UsernameAvailableProcessor by lazy {
        UsernameAvailableProcessor(
            usernamePolicy = usernamePolicyFixture,
            memberPersistencePort = memberPersistencePortFixture,
            messageResolver = messageResolverFixture,
        )
    }

    val nicknameAvailableProcessor: NicknameAvailableProcessor by lazy {
        NicknameAvailableProcessor(
            nicknamePolicy = nicknamePolicyFixture,
            memberPersistencePort = memberPersistencePortFixture,
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

    val registerMemberProcessor: RegisterMemberProcessor by lazy {
        RegisterMemberProcessor(
            memberPersistencePort = memberPersistencePortFixture,
            passwordEncryptionPort = passwordEncryptionPortFixture,
            emailVerificationPersistencePort = emailVerificationPersistencePortFixture,
        )
    }

    val loginProcessor: LoginProcessor by lazy {
        LoginProcessor(
            memberPersistencePort = memberPersistencePortFixture,
            passwordEncryptionPort = passwordEncryptionPortFixture,
            accessTokenGeneratePort = accessTokenPortFixture,
            refreshTokenHandler = refreshTokenHandler,
        )
    }

    val tokenRefreshProcessor: TokenRefreshProcessor by lazy {
        TokenRefreshProcessor(
            memberPersistencePort = memberPersistencePortFixture,
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

    val registerMemberUseCase: RegisterMemberUseCase by lazy {
        RegisterMemberUseCaseImpl(
            commandMapper = registerMemberCommandMapper,
            processor = registerMemberProcessor,
        )
    }

    val loginUseCase: LoginUseCaseImpl by lazy {
        LoginUseCaseImpl(
            commandMapper = loginCommandMapper,
            processor = loginProcessor,
        )
    }

    val tokenRefreshUseCase: TokenRefreshUseCase by lazy {
        TokenRefreshUseCaseImpl(
            commandMapper = tokenRefreshCommandMapper,
            processor = tokenRefreshProcessor,
        )
    }
}
