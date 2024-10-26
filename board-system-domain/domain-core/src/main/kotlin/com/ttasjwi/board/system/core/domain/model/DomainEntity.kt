package com.ttasjwi.board.system.core.domain.model

abstract class DomainEntity<ID: DomainId<*>>(
    id: ID? = null
) {

    var id: ID? = id
        private set

    fun initId(id: ID) {
        if (this.id != null) {
            throw IllegalStateException("ID가 이미 초기화된 상황입니다. (this.id = ${this.id}, id = $id)")
        }
        this.id = id
    }
}
