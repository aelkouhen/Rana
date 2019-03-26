package com.carhub.api.social.domain

import java.util._
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "post")
class Post extends Serializable {

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
  var message: String = _

  @BeanProperty
  @OneToOne
  var quotedPost: Post = _

  @BeanProperty
  @OneToOne
  var repliedTo: Post = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var creationTime: Date = _

  @BeanProperty
  @OneToOne
  var creator: Gearhead = _

  @BeanProperty
  @ManyToOne
  var thread: Thread = _

  @BeanProperty
  @ElementCollection
  var attachments: List[UUID] = new ArrayList[UUID]()
}
