package com.carhub.api.social.domain

import java.util._
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "message")
class Message extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var subject: String = _

  @BeanProperty
  var body: String = _

  @BeanProperty
  @OneToOne
  var repliedToMessage: Message = _

  @BeanProperty
  @OneToOne
  var transferredMessage: Message = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var creationTime: Date = _

  @BeanProperty
  @OneToOne
  var creator: Gearhead = _

  @BeanProperty
  @OneToMany(mappedBy = "message")
  var recipients: List[MessageRecipient] = new ArrayList[MessageRecipient]()

  @BeanProperty
  @ElementCollection
  var attachments: List[UUID] = new ArrayList[UUID]()
}
