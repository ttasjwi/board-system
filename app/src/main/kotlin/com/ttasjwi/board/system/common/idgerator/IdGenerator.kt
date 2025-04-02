package com.ttasjwi.board.system.common.idgerator

import java.util.random.RandomGenerator

/**
 * 고유 식별자 생성을 위한 클래스
 * X (구 Twitter)의 Snowflake 방식 사용
 *
 * 64비트 숫자 생성
 * 1비트(사용) + 41비트(시간) + 10비트(현재 애플리케이션의 고유 식별자 : 랜덤) + 12비트(동일 애플리케이션 동시요청시 순번)
 */
class IdGenerator private constructor() {

    companion object {
        private const val UNUSED_BITS: Int = 1
        private const val EPOCH_BITS: Int = 41
        private const val NODE_ID_BITS: Int = 10
        private const val SEQUENCE_BITS: Int = 12

        private const val MAX_NODE_ID: Long = (1L shl NODE_ID_BITS) - 1
        private const val MAX_SEQUENCE: Long = (1L shl SEQUENCE_BITS) - 1

        private val NODE_ID = RandomGenerator.getDefault().nextLong(MAX_NODE_ID + 1)

        // 2025-01-01T00:00:00+0900
        private val START_TIME_MILLIS = 1735657200000

        private var lastTimeMillis = START_TIME_MILLIS
        private var sequence = 0L

        fun create(): IdGenerator {
            return IdGenerator()
        }
    }

    @Synchronized
    fun nextId(): Long {
        // 현재 시간
        var currentTimeMillis = System.currentTimeMillis()

        // 만약 현재 시간이 lastTimeMillis보다 작다면, 시스템 클럭이 역행(rollback)된 것으로 판단하고 예외를 발생.
        if (currentTimeMillis < lastTimeMillis) {
            throw IllegalArgumentException("Invalid Time")
        }

        // 최근 아이디 발급 시점이, 지금 시각과 같을 때(같은 밀리초에서 여러 개의 ID 를 생성하는 경우)
        // 그 안에서 sequence 순번을 매김
        if (currentTimeMillis == lastTimeMillis) {
            // 순서(sequence) 를 1 증가 시키고, 혹시 최대 자리를 초과할 경우를 다시 버림 처리
            sequence = (sequence + 1) and MAX_SEQUENCE
            if (sequence == 0L) {
                currentTimeMillis = waitNextMillis(currentTimeMillis)
            }
        } else {
            sequence = 0
        }

        lastTimeMillis = currentTimeMillis


        return (((currentTimeMillis - START_TIME_MILLIS) shl (NODE_ID_BITS + SEQUENCE_BITS))
                or (NODE_ID shl SEQUENCE_BITS)
                or sequence)
    }

    /**
     * 현재 시간이 lastTimeMillis 와 같거나 그 이전일 경우, lastTimeMillis 보다 더 현재 시간이 커질 때까지 대기
     */
    private fun waitNextMillis(currentTimestamp: Long): Long {
        var currentTimestampVar = currentTimestamp
        while (currentTimestampVar <= lastTimeMillis) {
            currentTimestampVar = System.currentTimeMillis()
        }
        return currentTimestampVar
    }
}
