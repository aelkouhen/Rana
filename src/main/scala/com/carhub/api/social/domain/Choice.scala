package com.carhub.api.social.domain

import java.util._

import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "choice")
class Choice extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var text: String = _

  @BeanProperty
  @ManyToOne
  var poll: Poll = _

  @BeanProperty
  @OneToMany
  var voters: List[Gearhead] = new ArrayList[Gearhead]()
}
