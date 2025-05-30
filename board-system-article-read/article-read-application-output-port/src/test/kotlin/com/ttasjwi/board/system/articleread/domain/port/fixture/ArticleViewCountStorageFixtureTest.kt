package com.ttasjwi.board.system.articleread.domain.port.fixture

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-output-port] ArticleViewCountStorageFixture 테스트")
class ArticleViewCountStorageFixtureTest {

    private lateinit var articleViewCountStorageFixture: ArticleViewCountStorageFixture


    @BeforeEach
    fun setup() {
        articleViewCountStorageFixture = ArticleViewCountStorageFixture()
    }

    @Nested
    @DisplayName("readViewCount: 게시글의 조회수를 조회한다.")
    inner class ReadViewCountTest {

        @Test
        @DisplayName("조회수 저장 후 조회 테스트")
        fun testSuccess() {
            // given
            val articleId = 34L
            val viewCount = 348L


            articleViewCountStorageFixture.save(articleId, viewCount)

            // when
            val findViewCount = articleViewCountStorageFixture.readViewCount(articleId)

            // then
            assertThat(findViewCount).isEqualTo(viewCount)
        }


        @Test
        @DisplayName("조회수가 저장되어 있지 않다면, 조회수 조회 시도시, 0이 반환된다.")
        fun testNotFound() {
            // given
            // when
            val findViewCount = articleViewCountStorageFixture.readViewCount(131415L)

            // then
            assertThat(findViewCount).isEqualTo(0L)
        }

    }


    @Nested
    @DisplayName("readArticleViewCounts: 복수의 게시글들의 조회수를 가져온다.")
    inner class ReadArticleViewCountsTest {

        @Test
        @DisplayName("게시글 아이디별 조회수를 Map 형태로 가져온다.")
        fun test1() {
            // given
            for (i in 1L..10L) {
                articleViewCountStorageFixture.save(articleId = i, viewCount = i)
            }

            // when
            val articleIds = (1L..10L).toList()

            // then
            val viewCounts = articleViewCountStorageFixture.readViewCounts(articleIds)
            assertThat(viewCounts.size).isEqualTo(articleIds.size)
            for (i in 1L..10L) {
                assertThat(viewCounts[i]).isEqualTo(i)
            }
        }


        @Test
        @DisplayName("조회수가 없을 경우 0으로 가져와진다.")
        fun test2() {
            // given
            // when
            val articleIds = (1L..10L).toList()

            // then
            val viewCounts = articleViewCountStorageFixture.readViewCounts(articleIds)
            assertThat(viewCounts.size).isEqualTo(articleIds.size)
            for (i in 1L..10L) {
                assertThat(viewCounts[i]).isEqualTo(0L)
            }
        }


        @Test
        @DisplayName("빈 리스트를 전달할 경우 빈 Map 이 반환된다.")
        fun test3() {
            // given
            // when
            val viewCounts = articleViewCountStorageFixture.readViewCounts(emptyList())

            // then
            assertThat(viewCounts).isEmpty()
        }
    }
}
