package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaUser
import com.ttasjwi.board.system.article.infra.test.ArticleDataBaseIntegrationTest
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-database-adapter] ArticleWriterNicknamePersistenceAdapter 테스트")
class ArticleWriterNicknamePersistenceAdapterTest : ArticleDataBaseIntegrationTest() {

    @Nested
    @DisplayName("find : 저장 후 조회 테스트")
    inner class FindTest {

        @Test
        @DisplayName("저장된 사용자의 닉네임이 잘 조회되는 지 테스트")
        fun testCreate() {
            // given
            val user = JpaUser(
                userId = 12345L,
                email = "test@gmail.com",
                password = "1234567890",
                username = "test",
                nickname = "테스트",
                role = "USER",
                registeredAt = appDateTimeFixture(minute = 13).toLocalDateTime(),
            )

            // when
            entityManager.persist(user)
            flushAndClearEntityManager()
            val findWriterNickname = articleWriterNicknamePersistenceAdapter.findWriterNicknameOrNull(user.userId)!!

            // then
            assertThat(findWriterNickname).isEqualTo(user.nickname)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findWriterNickname = articleWriterNicknamePersistenceAdapter.findWriterNicknameOrNull(8485585858L)
            assertThat(findWriterNickname).isNull()
        }
    }
}
