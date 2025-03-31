package com.ttasjwi.board.system.common.idgenerator

import com.ttasjwi.board.system.common.idgerator.IdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Disabled // 수동테스트 목적. 테스트하려면 이 어노테이션을 주석처리 하고 실행해보세요.
@DisplayName("Snowflake 테스트")
class SnowflakeTest {

    private lateinit var idGenerator: IdGenerator

    @BeforeEach
    fun setup() {
        idGenerator = IdGenerator.create()
    }

    @Test
    @DisplayName("동시에 100만개 아이디를 생성하더라도 충돌이 일어나지 않는다.")
    fun nextIdTest() {
        // given
        val executorService = Executors.newFixedThreadPool(10)
        val futures: MutableList<Future<List<Long>>> = ArrayList()
        val repeatCount = 1000
        val idCount = 1000

        // when
        for (i in 1..repeatCount) {
            futures.add(executorService.submit<List<Long>> { generateIdList(idGenerator, idCount) })
        }

        // then
        val result: MutableList<Long> = ArrayList()
        for (future in futures) {
            val idList = future.get()
            for (i in 1..idList.lastIndex) {
                assertThat(idList[i]).isGreaterThan(idList[i - 1])
            }
            result.addAll(idList)
        }
        assertThat(result.stream().distinct().count()).isEqualTo((repeatCount * idCount).toLong())
        executorService.shutdown()
    }

    private fun generateIdList(idGenerator: IdGenerator, count: Int): List<Long> {
        val idList: MutableList<Long> = ArrayList()

        for (i in 1..count) {
            idList.add(idGenerator.nextId())
        }
        return idList
    }

    @Test
    @DisplayName("100만건의 Id 를 생성하는 성능 테스트 (거의 1초도 안 걸림)")
    fun nextIdPerformanceTest() {
        // given
        val executorService = Executors.newFixedThreadPool(10)
        val repeatCount = 1000
        val idCount = 1000
        val latch = CountDownLatch(repeatCount)

        // when
        val start = System.nanoTime()
        for (i in 1..repeatCount) {
            executorService.submit {
                generateIdList(idGenerator, idCount)
                latch.countDown()
            }
        }

        latch.await()

        val end = System.nanoTime()
        println("times = ${(end - start) / 1000000} ms")

        executorService.shutdown()
    }
}

