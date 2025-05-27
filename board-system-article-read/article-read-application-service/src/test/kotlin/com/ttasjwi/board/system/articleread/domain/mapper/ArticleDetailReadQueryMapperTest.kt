package com.ttasjwi.board.system.articleread.domain.mapper

import com.ttasjwi.board.system.articleread.domain.test.support.TestContainer
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.AuthUserLoaderFixture
import com.ttasjwi.board.system.common.auth.fixture.authUserFixture
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-application-service] ArticleDetailReadQueryMapper 테스트")
class ArticleDetailReadQueryMapperTest {

    private lateinit var articleDetailReadQueryMapper: ArticleDetailReadQueryMapper
    private lateinit var authUserLoaderFixture: AuthUserLoaderFixture


    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        articleDetailReadQueryMapper = container.articleDetailReadQueryMapper
        authUserLoaderFixture = container.authUserLoaderFixture
    }


    @Test
    @DisplayName("성공 - 인증사용자")
    fun testSuccess1() {
        // given
        val articleId = 13455L
        val authUser = authUserFixture(userId = 34L, role = Role.USER)
        authUserLoaderFixture.changeAuthUser(authUser)


        // when
        val query = articleDetailReadQueryMapper.mapToQuery(articleId)


        // then
        assertThat(query.articleId).isEqualTo(articleId)
        assertThat(query.authUser).isEqualTo(authUser)
    }


    @Test
    @DisplayName("성공 - 미인증 사용자")
    fun testSuccess2() {
        // given
        val articleId= 235L
        authUserLoaderFixture.changeAuthUser(null)

        // when
        val query = articleDetailReadQueryMapper.mapToQuery(articleId)

        // then
        assertThat(query.articleId).isEqualTo(articleId)
        assertThat(query.authUser).isNull()
    }
}
