package com.ttasjwi.board.system.common.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("DomainEntity, DomainId 테스트")
class DomainEntityAndIdTest {

    @Test
    @DisplayName("도메인 아이디들은 아이디 값(value)를 가진다.")
    fun domainIdTest1() {
        val idValue = 12L
        val testDomainId = TestDomainId(value = idValue)
        assertThat(testDomainId.value).isEqualTo(idValue)
    }

    @Test
    @DisplayName("도메인 엔티티는 최초에 아이디를 가지고 있지 않을 수 있다.")
    fun domainEntityTest2() {
        val testDomainEntity = TestDomainEntity()
        assertThat(testDomainEntity.id).isNull()
    }


    @Nested
    @DisplayName("InitId : 아이디 초기화")
    inner class InitId {
        @Test
        @DisplayName("initId: 아이디를 지정한 값으로 초기화한다.")
        fun test1() {
            val testDomainEntity = TestDomainEntity()
            val id = TestDomainId(144L)
            testDomainEntity.initId(id)

            assertThat(testDomainEntity.id).isEqualTo(id)
        }

        @Test
        @DisplayName("아이디가 초기화된 도메인 엔티티에 대해 다시 아이디 초기화를 할 수 없다")
        fun test2() {
            val testDomainEntity = TestDomainEntity()

            testDomainEntity.initId(TestDomainId(144L))

            assertThrows<IllegalStateException> { testDomainEntity.initId(TestDomainId(145L)) }
        }
    }

    class TestDomainId(value: Long) : DomainId<Long>(value) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is TestDomainId) return false
            return value == other.value
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): String {
            return "TestDomainId(value=$value)"
        }
    }

    class TestDomainEntity(id: TestDomainId? = null) : DomainEntity<TestDomainId>(id)
}
