package com.ttasjwi.board.system.member.domain.external.db.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaSocialConnectionRepository : JpaRepository<JpaSocialConnection, Long> {

    @Query(
        """
        SELECT sc FROM JpaSocialConnection as sc
        WHERE sc.socialService = :socialService AND sc.socialServiceUserId = :socialServiceUserId
    """
    )
    fun findBySocialServiceAndSocialServiceUserIdOrNull(
        @Param("socialService")socialService: String,
        @Param("socialServiceUserId")socialServiceUserId: String): JpaSocialConnection?
}
