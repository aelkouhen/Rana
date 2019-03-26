package com.carhub.api.social.domain

import java.util.{Date, UUID}

import com.carhub.api.social.domain.enumerations.{RelationshipKind, RelationshipStatus}
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "relationship")
class Relationship extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @Column(name = "RELATION_KIND")
  @BeanProperty
  @Enumerated(EnumType.STRING)
  var relationshipKind: RelationshipKind = _

  @BeanProperty
  @Enumerated(EnumType.STRING)
  var relationshipStatus: RelationshipStatus = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var since: Date = _

  @BeanProperty
  @Temporal(TemporalType.TIMESTAMP)
  var creationTime: Date = _

  @BeanProperty
  @ManyToOne
  var gearhead: Gearhead = _

  @BeanProperty
  @OneToOne
  var relatedTo: Gearhead = _
}
