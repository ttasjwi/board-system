package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("UsernameAvailableQueryMapper: UsernameAvailableRequest 를 UsernameAvailableQuery 로 변환한다")
class UsernameAvailableQueryMapperTest {

    private lateinit var queryMapper: UsernameAvailableQueryMapper

    @BeforeEach
    fun setup() {
        queryMapper = UsernameAvailableQueryMapper()
    }

    @Nested
    @DisplayName("mapToQuery: 사용자 요청을 애플리케이션 질의로 변환한다.")
    inner class MapToQuery {

        @Test
        @DisplayName("Username 이 누락되면 예외가 발생한다")
        fun testNullUsername() {
            val request = UsernameAvailableRequest(username = null)

            val exception = assertThrows<NullArgumentException> { queryMapper.mapToQuery(request) }
            assertThat(exception.source).isEqualTo("username")
        }

        @Test
        @DisplayName("Username 이 누락되지 않았다면 query 가 반환된다")
        fun testSuccess() {
            val request = UsernameAvailableRequest(username = "test")
            val query = queryMapper.mapToQuery(request)

            assertThat(query).isNotNull
            assertThat(query.username).isEqualTo(request.username)
        }
    }
}
