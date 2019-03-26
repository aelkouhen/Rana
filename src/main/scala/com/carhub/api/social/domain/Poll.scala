package com.carhub.api.social.domain

import java.util._

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "poll")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
class Poll extends Topic {

  @BeanProperty
  var subject: String = _

  @BeanProperty
  @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var choices: List[Choice] = new ArrayList[Choice]()

  @BeanProperty
  @ManyToOne
  var channel: Channel = _
}
