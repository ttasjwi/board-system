package com.ttasjwi.board.system.common.dataserializer

import com.ttasjwi.board.system.common.time.AppDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime

@DisplayName("DataSerializer: 데이터 직렬화, 역직렬화")
class DataSerializerTest {

    data class DataSerializerTestClass(
        val name: String,
        val age: Long,
        val nullField: String?,
        val createdAtZonedDateTime: ZonedDateTime,
        val createdAtLocalDateTime: LocalDateTime,
        val createdAtInstant: Instant,
    )

    @Test
    @DisplayName("Json 직렬화, 역직렬화 테스트")
    fun test() {
        // given
        val name = "땃쥐"
        val age = 20L
        val createdAt = AppDateTime.of(2025, 1, 1, 0, 0, 0)

        val data = DataSerializerTestClass(
            name = name,
            age = age,
            nullField = null,
            createdAtZonedDateTime = createdAt.toZonedDateTime(),
            createdAtLocalDateTime = createdAt.toLocalDateTime(),
            createdAtInstant = createdAt.toInstant(),
        )

        // when
        val json = DataSerializer.serialize(data)
        println(json)
        val deserializedData = DataSerializer.deserialize(json, DataSerializerTestClass::class.java)

        // then
        assertThat(json).isEqualTo("{\"name\":\"땃쥐\",\"age\":20,\"nullField\":null,\"createdAtZonedDateTime\":\"2025-01-01T00:00:00+09:00[Asia/Seoul]\",\"createdAtLocalDateTime\":\"2025-01-01T00:00:00\",\"createdAtInstant\":\"2024-12-31T15:00:00Z\"}")
        assertThat(deserializedData.name).isEqualTo(name)
        assertThat(deserializedData.age).isEqualTo(age)
        assertThat(deserializedData.nullField).isNull()
        assertThat(deserializedData.createdAtZonedDateTime).isEqualTo(createdAt.toZonedDateTime())
        assertThat(deserializedData.createdAtLocalDateTime).isEqualTo(createdAt.toLocalDateTime())
        assertThat(deserializedData.createdAtInstant).isEqualTo(createdAt.toInstant())
    }
}
