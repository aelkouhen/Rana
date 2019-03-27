package com.carhub.api.social.domain.dto

import scala.beans.BeanProperty

class Video extends Resource{

  @BeanProperty
  var (width, height) = (0, 0)

  @BeanProperty
  var definition: String = _


}
