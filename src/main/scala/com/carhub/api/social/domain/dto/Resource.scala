package com.carhub.api.social.domain.dto

import java.util.{Date, UUID}

import scala.beans.BeanProperty

abstract class Resource {

  @BeanProperty
  var id: UUID = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var caption: String = _

  @BeanProperty
  var size: Long = _

  @BeanProperty
  var mimeType: String = _

  @BeanProperty
  var format: String = _

  @BeanProperty
  var created: Date = _

  @BeanProperty
  var url: String = _

  @BeanProperty
  var content: Array[Byte] = _

  override def toString: String = {
    name
  }
}