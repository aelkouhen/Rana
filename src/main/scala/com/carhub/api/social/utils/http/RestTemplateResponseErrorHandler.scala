package com.carhub.api.social.utils.http

import com.carhub.api.social.utils.exception.ElementNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

class RestTemplateResponseErrorHandler extends ResponseErrorHandler{

  override def hasError(response: ClientHttpResponse): Boolean = (response
    .getStatusCode()
    .series() == HttpStatus.Series.CLIENT_ERROR || response
    .getStatusCode()
    .series() == HttpStatus.Series.SERVER_ERROR)

  override def handleError(response: ClientHttpResponse): Unit = {
    if (response.getStatusCode.series.equals(HttpStatus.Series.SERVER_ERROR)) {
      //Handle SERVER_ERROR
      println(response.getStatusCode + " " + response.getRawStatusCode)

    }
    else if (response.getStatusCode.series.equals(HttpStatus.Series.CLIENT_ERROR)) { //Handle CLIENT_ERROR
      if (response.getStatusCode.equals(HttpStatus.NOT_FOUND)) throw new ElementNotFoundException[Object](classOf[Object])
    }
  }

}
