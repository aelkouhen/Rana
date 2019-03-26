package com.carhub.api.social.repositories

import java.util.UUID

import com.carhub.api.social.domain.Channel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ChannelRepository extends JpaRepository[Channel, UUID] {}
