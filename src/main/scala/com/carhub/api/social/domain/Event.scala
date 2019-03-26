package com.carhub.api.social.domain

import java.util.{Date, UUID}
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "event")
class Event extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  var coverPhotoId: UUID = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var creationTime: Date = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var startTime: Date = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var endTime: Date = _

  @BeanProperty
  @OneToOne
  var creator: Gearhead = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var updateTime: Date = _

  @BeanProperty
  var location: String = _
}
