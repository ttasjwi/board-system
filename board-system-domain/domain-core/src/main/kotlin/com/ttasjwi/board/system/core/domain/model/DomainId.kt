package com.ttasjwi.board.system.core.domain.model

abstract class DomainId<T>(
    val value: T
) {

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
    abstract override fun toString(): String
}
