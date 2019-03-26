package com.carhub.api.social.domain

import java.util.UUID

import com.carhub.api.social.domain.enumerations.UserRole
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty


@Entity
@Table(name = "topic_membership")
class TopicMembership extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @Column(name = "USER_ROLE")
  @Enumerated(EnumType.STRING)
  var userType: UserRole = _

  @BeanProperty
  @ManyToOne
  var gearhead: Gearhead = _

  @BeanProperty
  @OneToOne
  var topic: Topic = _
}
