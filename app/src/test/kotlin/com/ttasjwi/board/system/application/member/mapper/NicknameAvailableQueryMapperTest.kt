package com.ttasjwi.board.system.application.member.mapper

import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.global.exception.NullArgumentException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("NicknameAvailableQueryMapper: NicknameAvailableRequest 를 NicknameAvailableQuery 로 변환한다")
class NicknameAvailableQueryMapperTest {

    private lateinit var queryMapper: NicknameAvailableQueryMapper

    @BeforeEach
    fun setup() {
        queryMapper = NicknameAvailableQueryMapper()
    }

    @Nested
    @DisplayName("mapToQuery: 사용자 요청을 애플리케이션 질의로 변환한다.")
    inner class MapToQuery {

        @Test
        @DisplayName("닉네임이 누락되면 예외가 발생한다")
        fun testNullNickname() {
            val request = NicknameAvailableRequest(nickname = null)

            val exception = assertThrows<NullArgumentException> { queryMapper.mapToQuery(request) }
            assertThat(exception.source).isEqualTo("nickname")
        }

        @Test
        @DisplayName("닉네임이 누락되지 않았다면 query 가 반환된다")
        fun testSuccess() {
            val request = NicknameAvailableRequest(nickname = "test")
            val query = queryMapper.mapToQuery(request)

            assertThat(query).isNotNull
            assertThat(query.nickname).isEqualTo(request.nickname)
        }
    }
}
