package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.application.fixture.TransactionRunnerFixture
import com.ttasjwi.board.system.member.application.mapper.NicknameAvailableQueryMapper
import com.ttasjwi.board.system.member.application.processor.NicknameAvailableProcessor
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.member.domain.service.fixture.MemberStorageFixture
import com.ttasjwi.board.system.member.domain.service.fixture.NicknameCreatorFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("NicknameAvailableService: 닉네임 사용 가능 여부 확인을 수행하는 애플리케이션 서비스")
class NicknameAvailableApplicationServiceTest {

    private lateinit var applicationService: NicknameAvailableApplicationService

    @BeforeEach
    fun setup() {
        applicationService = NicknameAvailableApplicationService(
            queryMapper = NicknameAvailableQueryMapper(),
            processor = NicknameAvailableProcessor(
                nicknameCreator = NicknameCreatorFixture(),
                memberFinder = MemberStorageFixture(),
            ),
            transactionRunner = TransactionRunnerFixture()
        )
    }

    @Test
    @DisplayName("닉네임 유효성 검사를 마친 결과를 성공적으로 반환한다.")
    fun testSuccess() {
        val request = NicknameAvailableRequest("hello")
        val result = applicationService.checkNicknameAvailable(request)

        assertThat(result).isNotNull
        assertThat(result.nickname).isEqualTo(request.nickname)
        assertThat(result.isAvailable).isTrue()
        assertThat(result.reasonCode).isEqualTo("NicknameAvailableCheck.Available")
    }
}
