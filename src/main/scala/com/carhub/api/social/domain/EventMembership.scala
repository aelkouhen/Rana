package com.carhub.api.social.domain

import java.util.UUID

import com.carhub.api.social.domain.enumerations.{EventParticipationStatus, UserRole}
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty


@Entity
@Table(name = "event_membership")
class EventMembership extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  @Enumerated
  var status: EventParticipationStatus = _

  @Column(name = "USER_ROLE")
  @Enumerated(EnumType.STRING)
  var userType: UserRole = _

  @BeanProperty
  @OneToOne
  var event: Event = _

  @BeanProperty
  @ManyToOne
  var gearhead: Gearhead = _
}
