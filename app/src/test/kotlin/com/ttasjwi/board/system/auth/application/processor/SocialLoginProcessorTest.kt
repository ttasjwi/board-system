package com.ttasjwi.board.system.auth.application.processor

import com.ttasjwi.board.system.auth.application.dto.SocialLoginCommand
import com.ttasjwi.board.system.auth.application.usecase.SocialLoginResponse
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.*
import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.member.domain.model.SocialService
import com.ttasjwi.board.system.member.domain.model.fixture.memberFixture
import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import com.ttasjwi.board.system.member.domain.service.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginProcessor: 소셜 로그인 애플리케이션 명령을 실질적으로 처리한다")
class SocialLoginProcessorTest {

    private lateinit var socialLoginProcessor: SocialLoginProcessor
    private lateinit var memberStorageFixture: MemberStorageFixture
    private lateinit var socialConnectionStorageFixture: SocialConnectionStorageFixture
    private lateinit var usernameManagerFixture: UsernameManagerFixture
    private lateinit var nicknameCreatorFixture: NicknameManagerFixture
    private lateinit var refreshTokenHolderStorageFixture: RefreshTokenHolderStorageFixture

    @BeforeEach
    fun setup() {
        memberStorageFixture = MemberStorageFixture()
        socialConnectionStorageFixture = SocialConnectionStorageFixture()
        usernameManagerFixture = UsernameManagerFixture()
        nicknameCreatorFixture = NicknameManagerFixture()
        refreshTokenHolderStorageFixture = RefreshTokenHolderStorageFixture()
        socialLoginProcessor = SocialLoginProcessor(
            memberFinder = memberStorageFixture,
            socialConnectionCreator = SocialConnectionCreatorFixture(),
            socialConnectionStorage = socialConnectionStorageFixture,
            passwordManager = PasswordManagerFixture(),
            usernameManager = usernameManagerFixture,
            nicknameManager = nicknameCreatorFixture,
            memberCreator = MemberCreatorFixture(),
            memberAppender = memberStorageFixture,
            authMemberCreator = AuthMemberCreatorFixture(),
            accessTokenManager = AccessTokenManagerFixture(),
            refreshTokenManager = RefreshTokenManagerFixture(),
            refreshTokenHolderFinder = refreshTokenHolderStorageFixture,
            refreshTokenHolderManager = RefreshTokenHolderManagerFixture(),
            refreshTokenHolderAppender = refreshTokenHolderStorageFixture
        )
    }


    @Test
    @DisplayName("소셜 연동에 해당하는 회원이 있으면, 로그인 처리한다.")
    fun test1() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val member = memberStorageFixture.save(memberFixture(email = email))
        socialConnectionStorageFixture.save(
            socialConnectionFixture(
                id = 15567L,
                memberId = member.id,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val result = socialLoginProcessor.socialLogin(command)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenType).isEqualTo("Bearer")
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isFalse()
        assertThat(result.createdMember).isNull()
    }

    @Test
    @DisplayName("소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 생성 후 로그인 처리한다.")
    fun test2() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val member = memberStorageFixture.save(memberFixture(email = email))

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val result = socialLoginProcessor.socialLogin(command)

        // then
        val findSocialConnection = socialConnectionStorageFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!

        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isFalse()
        assertThat(result.createdMember).isNull()
        assertThat(findSocialConnection.id).isNotNull
        assertThat(findSocialConnection.memberId).isEqualTo(member.id)
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
    }

    @Test
    @DisplayName("소셜 연동도 없고, 이메일에 해당하는 회원이 없으면, 회원 가입 및 소셜 연동 생성 후 로그인 처리한다.")
    fun test3() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val result = socialLoginProcessor.socialLogin(command)

        // then
        val findSocialConnection = socialConnectionStorageFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!
        val findMember = memberStorageFixture.findByIdOrNull(findSocialConnection.memberId)!!

        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isTrue()
        assertThat(result.createdMember).isEqualTo(
            SocialLoginResponse.CreatedMember(
                memberId = findMember.id.toString(),
                email = findMember.email,
                username = findMember.username,
                nickname = findMember.nickname,
                role = findMember.role.name,
                registeredAt = findMember.registeredAt.toZonedDateTime()
            )
        )
        assertThat(findSocialConnection.id).isNotNull
        assertThat(findSocialConnection.memberId).isNotNull
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
        assertThat(findMember.id).isEqualTo(findSocialConnection.memberId)
        assertThat(findMember.email).isEqualTo(command.email)
        assertThat(findMember.username).isNotNull
        assertThat(findMember.nickname).isNotNull
        assertThat(findMember.password).isNotNull
        assertThat(findMember.role).isEqualTo(Role.USER)
        assertThat(findMember.registeredAt).isEqualTo(command.currentTime)
    }


    @Test
    @DisplayName("소셜 로그인 시점에, 리프레시 토큰 홀더가 저장되어 있다면 생성되지 않고 조회된다")
    fun test4() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val member = memberStorageFixture.save(memberFixture(email = email))
        socialConnectionStorageFixture.save(
            socialConnectionFixture(
                id = 14567L,
                memberId = member.id,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )
        refreshTokenHolderStorageFixture.append(
            memberId = member.id,
            refreshTokenHolder = refreshTokenHolderFixture(
                memberId = member.id,
                role = member.role,
                tokens = mutableMapOf(
                    refreshTokenIdFixture("tokenId1") to refreshTokenFixture(
                        memberId = member.id,
                        refreshTokenId = "tokenId1",
                        tokenValue = "tokenValue",
                        issuedAt = appDateTimeFixture(minute = 0),
                    )
                )
            ),
            currentTime = appDateTimeFixture(minute = 0),
        )

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val result = socialLoginProcessor.socialLogin(command)
        val findRefreshTokenHolder = refreshTokenHolderStorageFixture.findByMemberIdOrNull(member.id)!!

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.memberCreated).isFalse()
        assertThat(result.createdMember).isNull()
        assertThat(findRefreshTokenHolder.getTokens().size).isEqualTo(2)
    }
}
