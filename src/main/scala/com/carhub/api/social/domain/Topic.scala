package com.carhub.api.social.domain

import java.util.{Date, UUID}
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
abstract class Topic extends Serializable{

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var coverPhotoId: UUID = _

  @BeanProperty
  var logoId: UUID = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var creationTime: Date = _

  @BeanProperty
  @OneToOne
  var creator: Gearhead = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var updateTime: Date = _
}
