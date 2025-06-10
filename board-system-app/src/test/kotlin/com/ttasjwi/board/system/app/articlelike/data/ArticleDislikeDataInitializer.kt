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
class ArticleDislikeDataInitializer {

    companion object {
        private const val USER_COUNT = 12_000_000L
        private const val ARTICLE_COUNT = 24_000_000L
        private val baseTime = LocalDateTime.of(2025, 6, 10, 0, 0)
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        private var articleDislikeId = 0L
    }

    @Test
    @DisplayName("게시글 싫어요 데이터 csv 파일 생성")
    fun initialize() {
        val csvFile = File("article-dislikes.csv")
        val random = Random()

        csvFile.bufferedWriter().use { writer ->

            // 2400만 게시글에 대해서
            for (articleId in 1L..ARTICLE_COUNT) {
                // 60% 확률로 싫어요, 40% 확률로 싫어요 안 함
                if (shouldDislike(random)) {
                    makeDislike(articleId, random,  writer)
                }
            }
        }
    }

    private fun shouldDislike(random: Random): Boolean {
        val randomDouble = random.nextDouble()

        // 60% 확률로 싫어요
        return randomDouble < 0.6
    }

    private fun makeDislike(articleId: Long, random: Random, writer:BufferedWriter) {
        val randomDouble = random.nextDouble()

        // 싫어요 1개
        if (randomDouble < 0.5) {
            val userId = generateRandomUserId(random)
            writeArticleDislike(++articleDislikeId, articleId, userId, writer)
            return
        }
        // 싫어요 2개
        val userId1 = generateRandomUserId(random)

        var userId2 = generateRandomUserId(random)
        while (userId1 == userId2) {
            userId2 = generateRandomUserId(random)
        }
        writeArticleDislike(++articleDislikeId, articleId, userId1, writer)
        writeArticleDislike(++articleDislikeId, articleId, userId2, writer)
        return
    }

    private fun generateRandomUserId(random: Random): Long {
        return random.nextLong(USER_COUNT) + 1L
    }

    private fun writeArticleDislike(articleDislikeId: Long, articleId: Long, userId: Long, writer: BufferedWriter) {
        writer.write("${articleDislikeId},$articleId,$userId,${baseTime.plusSeconds(articleDislikeId).format(formatter)}\n")
        if (articleDislikeId % 100_000L == 0L) {
            println("Written $articleDislikeId lines...")
        }
    }
}
