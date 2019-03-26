package com.carhub.api.social.repositories

import java.util.UUID

import com.carhub.api.social.domain.Thread
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ThreadRepository extends JpaRepository[Thread, UUID] {}
