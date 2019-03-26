package com.carhub.api.social.domain

import java.util.UUID

import com.carhub.api.social.domain.enumerations.MessageStatus
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty


@Entity
@Table(name = "message_recipient")
class MessageRecipient extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  @ManyToOne
  var message: Message = _

  @BeanProperty
  @OneToOne
  var recipient: Gearhead = _

  @BeanProperty
  @Enumerated(EnumType.STRING)
  var status: MessageStatus = _
}
