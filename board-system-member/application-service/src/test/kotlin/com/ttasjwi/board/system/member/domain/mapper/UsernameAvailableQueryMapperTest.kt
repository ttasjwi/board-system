package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.fixture.LocaleResolverFixture
import com.ttasjwi.board.system.member.domain.UsernameAvailableRequest
import com.ttasjwi.board.system.member.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.util.*

@DisplayName("UsernameAvailableQueryMapper: UsernameAvailableRequest 를 UsernameAvailableQuery 로 변환한다")
class UsernameAvailableQueryMapperTest {

    private lateinit var queryMapper: UsernameAvailableQueryMapper
    private lateinit var localeResolverFixture: LocaleResolverFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        localeResolverFixture = container.localeResolverFixture
        queryMapper = container.usernameAvailableQueryMapper
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
            localeResolverFixture.changeLocale(Locale.CHINESE)
            val request = UsernameAvailableRequest(username = "test")
            val query = queryMapper.mapToQuery(request)

            assertThat(query).isNotNull
            assertThat(query.username).isEqualTo(request.username)
            assertThat(query.locale).isEqualTo(Locale.CHINESE)
        }
    }
}
