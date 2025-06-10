package com.ttasjwi.board.system.app.articlelike.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.BufferedWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random

@Disabled
class ArticleLikeDataInitializer {

    companion object {
        private const val USER_COUNT = 12_000_000L
        private const val ARTICLE_COUNT = 24_000_000L
        private val baseTime = LocalDateTime.of(2025, 6, 10, 0, 0)
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        private var articleLikeId = 0L
    }

    @Test
    @DisplayName("게시글 좋아요 데이터 csv 파일 생성")
    fun initialize() {
        val csvFile = File("article-likes.csv")
        val random = Random()

        csvFile.bufferedWriter().use { writer ->

            // 2400만 게시글에 대해서
            for (articleId in 1L..ARTICLE_COUNT) {
                // 40% 확률로 좋아요, 40% 확률로 좋아요 안 함
                if (shouldLike(random)) {
                    makeLike(articleId, random,  writer)
                }
            }
        }
    }

    private fun shouldLike(random: Random): Boolean {
        val randomDouble = random.nextDouble()

        // 40% 확률로 좋아요
        return randomDouble < 0.4
    }

    private fun makeLike(articleId: Long, random: Random, writer:BufferedWriter) {
        val randomDouble = random.nextDouble()

        // 좋아요 1개
        if (randomDouble < 0.5) {
            val userId = generateRandomUserId(random)
            writeArticleLike(++articleLikeId, articleId, userId, writer)
            return
        }
        // 좋아요 2개
        val userId1 = generateRandomUserId(random)

        var userId2 = generateRandomUserId(random)
        while (userId1 == userId2) {
            userId2 = generateRandomUserId(random)
        }
        writeArticleLike(++articleLikeId, articleId, userId1, writer)
        writeArticleLike(++articleLikeId, articleId, userId2, writer)
        return
    }

    private fun generateRandomUserId(random: Random): Long {
        return random.nextLong(USER_COUNT) + 1L
    }

    private fun writeArticleLike(articleLikeId: Long, articleId: Long, userId: Long, writer: BufferedWriter) {
        writer.write("${articleLikeId},$articleId,$userId,${baseTime.plusSeconds(articleLikeId).format(formatter)}\n")
        if (articleLikeId % 100_000L == 0L) {
            println("Written $articleLikeId lines...")
        }
    }
}
