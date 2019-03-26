package com.carhub.api.social.domain

import java.util._
import javax.persistence._
import scala.beans.BeanProperty

@Entity
@Table(name = "channel")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
class Channel extends Topic {

  @BeanProperty
  var name: String = _

  @BeanProperty
  @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var treads: List[Thread] = new ArrayList[Thread]()

  @BeanProperty
  @OneToMany(mappedBy = "channel", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var polls: List[Poll] = new ArrayList[Poll]()
}
