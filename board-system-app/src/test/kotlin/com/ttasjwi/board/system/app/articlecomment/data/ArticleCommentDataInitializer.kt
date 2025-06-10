package com.ttasjwi.board.system.app.articlecomment.data

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.BufferedWriter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random

@Disabled
class ArticleCommentDataInitializer {

    companion object {
        private const val USER_COUNT = 12_000_000L
        private const val ARTICLE_COUNT = 24_000_000L
        private const val COMMENT_COUNT = 24_000_000L
        private const val TARGET_ARTICLE_ID = 24_000_000L
        private val baseTime = LocalDateTime.of(2025, 6, 10, 0, 0)
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // 게시글 별로, 최근 0-1개의 댓글 보관
        // 게시글 Id -> (댓글Id, 댓글 작성자 Id)
        // 최근 10만건정도까지 유지(오래된 순으로 만료됨)
        private val articleCommentHistories = object : LinkedHashMap<Long, Pair<Long, Long>>(10_000, 0.75f, true) {
            override fun removeEldestEntry(eldest: Map.Entry<Long, Pair<Long, Long>>): Boolean {
                return size > 100_000
            }
        }
    }

    @Test
    @DisplayName("게시글 댓글 데이터 csv 파일 생성")
    fun initialize() {
        val csvFile = File("article-comments.csv")
        val random = Random()

        csvFile.bufferedWriter().use { writer ->

            // 2400만 댓글 작성
            for (articleCommentId in 1L..COMMENT_COUNT) {
                // 50% 확률로 target 게시글에 댓글 작성
                if (shouldWriterOnTargetArticle(random)) {
                    makeCommentOnArticle(articleCommentId, TARGET_ARTICLE_ID, random, writer)
                } else {
                    // 그 외의 경우에는 나머지 게시글에 댓글 랜덤 작성
                    val randomArticleId = random.nextLong(ARTICLE_COUNT - 1) + 1L
                    makeCommentOnArticle(articleCommentId, randomArticleId, random, writer)
                }

            }
        }
    }

    private fun shouldWriterOnTargetArticle(random: Random): Boolean {
        val randomDouble = random.nextDouble()

        // 50% 확률로 좋아요
        return randomDouble < 0.5
    }

    private fun makeCommentOnArticle(articleCommentId: Long, articleId: Long, random: Random, writer: BufferedWriter) {
        val writerId = random.nextLong(USER_COUNT) + 1L

        // 해당 게시글에 댓글이 있는 경우, 대댓글 작성
        if (hasParentComment(articleId)) {
            val (parentCommentId, parentCommentWriterId) = getParentCommentInfo(articleId)

            writeComment(
                articleCommentId = articleCommentId,
                articleId = articleId,
                rootParentCommentId = parentCommentId,
                writerId = writerId,
                parentCommentWriterId = parentCommentWriterId,
                writer = writer
            )
            removeCommentHistory(articleId)
        } else {
            // 댓글이 없다면, 루트댓글 작성
            writeComment(
                articleCommentId = articleCommentId,
                articleId = articleId,
                rootParentCommentId = articleCommentId,
                writerId = writerId,
                parentCommentWriterId = null,
                writer = writer
            )
            saveCommentHistory(articleId, articleCommentId, writerId)
        }
    }

    private fun hasParentComment(articleId: Long): Boolean {
        return articleCommentHistories.containsKey(articleId)
    }

    private fun getParentCommentInfo(articleId: Long): Pair<Long, Long> {
        return articleCommentHistories[articleId]!!
    }

    private fun removeCommentHistory(articleId: Long) {
        articleCommentHistories.remove(articleId)
    }

    private fun saveCommentHistory(articleId: Long, articleCommentId: Long, commentWriterId : Long) {
        articleCommentHistories[articleId] = Pair(articleCommentId, commentWriterId)
    }


    private fun writeComment(
        articleCommentId: Long,
        articleId: Long,
        rootParentCommentId: Long,
        writerId: Long,
        parentCommentWriterId: Long?,
        writer: BufferedWriter) {

        val content = makeContent(articleCommentId)
        val writerNickname = makeUserNickname(writerId)
        val parentCommentWriterNickname = parentCommentWriterId?.let { makeUserNickname(it) }
        val createdAt = baseTime.plusSeconds(articleCommentId).format(formatter)
        val modifiedAt = createdAt
        writer.write(
            "$articleCommentId,$content,$articleId,$rootParentCommentId,$writerId,$writerNickname,${parentCommentWriterId ?: ""},${parentCommentWriterNickname ?: ""},NOT_DELETED,$createdAt,$modifiedAt\n"
        )

        if (articleCommentId % 100_000L == 0L) {
            println("Written $articleCommentId lines...")
        }
    }

    private fun makeContent(articleCommentId: Long): String {
        return "content${makePaddedId(articleCommentId)}"
    }

    private fun makeUserNickname(userId: Long): String {
        return "user${makePaddedId(userId)}"
    }

    private fun makePaddedId(id: Long): String {
        return id.toString().padStart(8, '0')
    }
}
