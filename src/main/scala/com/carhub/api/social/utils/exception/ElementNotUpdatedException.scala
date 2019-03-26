package com.carhub.api.social.utils.exception

class ElementNotUpdatedException[A](val element : Class[A]) extends RuntimeException{
  override def getMessage: String = "No element of type " + element.getSimpleName + " was updated"
}
