package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.core.domain.model.DomainId

class MemberId(
    value: Long
) : DomainId<Long>(value) {

    companion object {

        fun restore(value: Long): MemberId {
            return MemberId(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemberId) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "MemberId(value=$value)"
    }
}
