package com.ttasjwi.board.system.app.article.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Disabled
class ArticleDataInitializer {

    @Test
    @DisplayName("게시글 벌크 삽입 csv 파일 생성")
    fun initialize() {
        val csvFile = File("articles.csv")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val totalArticleCount = 24_000_000L
        val random = Random()

        csvFile.bufferedWriter().use { writer ->

            for (articleId in 1L..totalArticleCount) {
                val paddedId = articleId.toString().padStart(8, '0')
                val title = "title$paddedId"
                val content = "content$paddedId"
                val (boardId, articleCategoryId) = getRandomBoardArticleCategoryId(random)
                val (writerId, writerNickname) = getRandomWriter(random)
                val baseTime = LocalDateTime.of(2025, 6, 10, 0, 0)
                val createdAt = baseTime.plusSeconds(articleId).format(formatter)
                val modifiedAt = createdAt
                writer.write("$articleId,$title,$content,$boardId,$articleCategoryId,$writerId,$writerNickname,$createdAt,$modifiedAt\n")

                if (articleId % 100000 == 0L) {
                    println("Written $articleId lines...")
                }
            }
        }
    }

    private fun getRandomBoardArticleCategoryId(random: Random): Pair<Long, Long> {
        val otherBoards = (2..500L).toList()  // 499개 게시판

        // 50% 의 확률로 게시판 1번에 게시글 작성, 나머지 50% 확률을 499개 게시판이 분할
        val boardId = if (random.nextDouble() < 0.5) {
            1L
        } else {
            otherBoards[random.nextInt(otherBoards.size)]
        }

        val articleCategoryId = 4 * (boardId - 1) + random.nextLong(3L) + 1 // (일반, 질문, 정보)

        return Pair(boardId, articleCategoryId)
    }

    private fun getRandomWriter(random: Random): Pair<Long, String> {
        val userId = random.nextLong(12_000_000L) + 1L
        val userNickname = "user${userId.toString().padStart(8, '0')}"
        return Pair(userId, userNickname)
    }
}
