package com.carhub.api.social.domain

import java.util._
import java.util.{Date, UUID}
import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "photo_album")
class PhotoAlbum extends Serializable {

  @Id
  @BeanProperty
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  var albumId: Long = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var coverPhotoId: UUID = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  @Column(name = "CREATION_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  var created: Date = _

  @BeanProperty
  @Column(name = "MODIFICATION_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  var modified: Date = _

  @BeanProperty
  var location: String = _

  @BeanProperty
  @ElementCollection
  var photo: List[UUID] = new ArrayList[UUID]()

  @BeanProperty
  @ManyToOne
  var gearhead: Gearhead = _
}
