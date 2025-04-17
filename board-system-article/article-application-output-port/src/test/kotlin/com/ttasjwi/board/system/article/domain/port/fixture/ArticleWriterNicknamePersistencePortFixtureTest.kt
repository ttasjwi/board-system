package com.ttasjwi.board.system.article.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ArticleWriterNicknamePersistencePortFixtureTest {

    private lateinit var articleWriterNicknamePersistencePortFixture: ArticleWriterNicknamePersistencePortFixture

    @BeforeEach
    fun setup() {
        articleWriterNicknamePersistencePortFixture = ArticleWriterNicknamePersistencePortFixture()
    }

    @Nested
    @DisplayName("find : 저장 후 조회 테스트")
    inner class FindTest {

        @Test
        @DisplayName("저장된 것이 잘 조회되는 지 테스트")
        fun testCreate() {
            // given
            val writerId = 1567L
            val writerNickname = "땃쥐"

            // when
            articleWriterNicknamePersistencePortFixture.save(
                writerId = writerId,
                writerNickname = writerNickname
            )
            val findWriterNickname = articleWriterNicknamePersistencePortFixture.findWriterNicknameOrNull(writerId = writerId)!!

            // then
            assertThat(findWriterNickname).isEqualTo(writerNickname)
        }

        @Test
        @DisplayName("조회 결과물이 없으면 null 을 반환하는 지 테스트")
        fun testNotFound() {
            val findWriterNickname = articleWriterNicknamePersistencePortFixture.findWriterNicknameOrNull(writerId = 128888L)
            assertThat(findWriterNickname).isNull()
        }
    }
}

