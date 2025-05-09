package com.ttasjwi.board.system.common.page

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("[common-core] PageReadUtil: 페이징 관련 값 계산을 위한 유틸 함수들")
class PageReadUtilTest {

    @Nested
    @DisplayName("calculatePageLimit: 현재 페이지, 페이지 크기, 접근 가능 페이지 갯수를 기반으로 limit 계산")
    inner class CalculatePageLimitTest {

        @Test
        @DisplayName("현재 페이지가 1, 페이지 사이즈 30, 접근 가능한 페이지가 10페이지 단위일 경우, limit 은 301")
        fun test1() {
            // given
            val page = 1L
            val pageSize = 30L
            val movablePageCount = 10L

            // when
            val pageLimit = calculatePageLimit(
                page = page,
                pageSize = pageSize,
                movablePageCount = movablePageCount
            )

            // then
            assertThat(pageLimit).isEqualTo(301)
        }

        @Test
        @DisplayName("현재 페이지가 17, 페이지 사이즈 50, 접근 가능한 페이지가 10페이지 단위일 경우, limit 은 1001")
        fun test2() {
            // given
            val page = 17L
            val pageSize = 50L
            val movablePageCount = 10L

            // when
            val pageLimit = calculatePageLimit(
                page = page,
                pageSize = pageSize,
                movablePageCount = movablePageCount
            )

            // then
            assertThat(pageLimit).isEqualTo(1001)
        }
    }

    @Nested
    @DisplayName("calculateOffSet: 페이징 조회를 시작할 OffSet 을 계산한다.")
    inner class CalculateOffSetTest {

        @Test
        @DisplayName("페이지당 5개 게시글, 1페이지 시작일 경우 OffSet 은 0")
        fun test1() {
            // given
            val page = 1L
            val pageSize = 5L

            // when
            val offSet = calculateOffSet(page, pageSize)

            // then
            assertThat(offSet).isEqualTo(0)
        }

        @Test
        @DisplayName("페이지당 5개 게시글, 2페이지 시작일 경우 OffSet 은 5")
        fun test2() {
            // given
            val page = 2L
            val pageSize = 5L

            // when
            val offSet = calculateOffSet(page, pageSize)

            // then
            assertThat(offSet).isEqualTo(5)
        }

        @Test
        @DisplayName("페이지당 10개 게시글, 10페이지 시작일 경우 OffSet 은 90")
        fun test3() {
            // given
            val page = 10L
            val pageSize = 10L

            // when
            val offSet = calculateOffSet(page, pageSize)

            // then
            assertThat(offSet).isEqualTo(90)
        }
    }
}
