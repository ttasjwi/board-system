package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.domain.model.DomainId

class SocialConnectionId
internal constructor(
    value: Long,
) : DomainId<Long>(value) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SocialConnectionId) return false
        return (value == other.value)
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "SocialConnectionId(value=$value)"
    }

    companion object {
        fun restore(value: Long): SocialConnectionId {
            return SocialConnectionId(value)
        }
    }
}
