package com.carhub.api.social.repositories

import java.util.UUID

import com.carhub.api.social.domain.Choice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ChoiceRepository extends JpaRepository[Choice, UUID] {}
