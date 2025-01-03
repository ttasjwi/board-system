package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.core.domain.model.DomainId

class BoardId(
    value: Long,
) : DomainId<Long>(value) {

    companion object {

        fun restore(value: Long): BoardId {
            return BoardId(value)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BoardId) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "BoardId(value=$value)"
    }
}
