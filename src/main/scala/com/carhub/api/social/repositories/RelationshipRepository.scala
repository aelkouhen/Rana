package com.carhub.api.social.repositories

import java.util.UUID

import com.carhub.api.social.domain.Relationship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait RelationshipRepository extends JpaRepository[Relationship, UUID] {}
