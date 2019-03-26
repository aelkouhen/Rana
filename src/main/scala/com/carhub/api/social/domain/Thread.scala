package com.carhub.api.social.domain

import java.util._

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "thread")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
class Thread extends Topic {

  @BeanProperty
  var subject: String = _

  @BeanProperty
  @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var posts: List[Post] = new ArrayList[Post]()

  @BeanProperty
  var viewCount: Long = _

  @BeanProperty
  @ManyToOne
  var channel: Channel = _
}
