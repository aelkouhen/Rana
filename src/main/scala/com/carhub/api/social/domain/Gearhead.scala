package com.carhub.api.social.domain

import java.io.Serializable
import java.util._
import com.carhub.api.social.domain.enumerations.Gender
import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}

import scala.beans.BeanProperty

@Entity
@Table(name = "gearhead")
class Gearhead extends Serializable{

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var aboutMe: String = _

  @BeanProperty
  var firstName: String = _

  @BeanProperty
  var lastName: String = _

  @BeanProperty
  @Temporal(TemporalType.DATE)
  var birthDay: Date = _

  @BeanProperty
  @Column(name="username", unique=true)
  var username: String = _

  @BeanProperty
  @Column(name = "LAST_CONNEXION")
  @Temporal(TemporalType.TIMESTAMP)
  var updateTime: Date = _

  @BeanProperty
  @Enumerated(EnumType.STRING)
  var gender: Gender = _

  @BeanProperty
  var currentLocation: String = _

  @BeanProperty
  @Type(`type` = "uuid-char")
  var profilePhotoId: UUID = _

  @BeanProperty
  @Type(`type` = "uuid-char")
  var coverPhotoId: UUID = _

  @BeanProperty
  @OneToMany(mappedBy = "gearhead", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var photoAlbums: List[PhotoAlbum] = _

  @BeanProperty
  @OneToMany(mappedBy = "gearhead", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var relationship: List[Relationship] = _

  @BeanProperty
  @OneToMany(mappedBy = "gearhead", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var topicMemberships: List[TopicMembership] = _

  @BeanProperty
  @OneToMany(mappedBy = "gearhead", fetch = FetchType.LAZY, cascade = Array(CascadeType.ALL))
  var eventMemberships: List[EventMembership] = _
}
