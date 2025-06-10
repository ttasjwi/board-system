package com.ttasjwi.board.system.app.articleview.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.random.Random

@Disabled
class ArticleViewCountDataInitializer {

    @Test
    @DisplayName("게시글 조회수 데이터 생성")
    fun initialize() {
        val startId = 23000001L
        val endId = 24000000L
        val outputFile = File("article-view-counts.txt")

        outputFile.bufferedWriter().use { writer ->
            for (articleId in startId..endId) {

                // 10% 확률로 조회수 가짐
                if (Random.nextDouble() < 0.1) {
                    val increment = Random.nextInt(1, 1001)
                    val command = "INCRBY board-system::article-view::article::$articleId::view-count $increment"
                    writer.write(command)
                    writer.newLine()
                }
            }
        }
    }
}
