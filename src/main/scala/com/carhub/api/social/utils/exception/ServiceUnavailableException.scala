package com.carhub.api.social.utils.exception

class ServiceUnavailableException (val serviceName : String) extends RuntimeException{
  override def getMessage: String = serviceName + " Service is down"
}
