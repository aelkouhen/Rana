package com.carhub.api.social.utils.http

import org.springframework.http.client.{ClientHttpRequestExecution, ClientHttpRequestInterceptor, ClientHttpResponse}
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest

class CustomClientHttpRequestInterceptor extends ClientHttpRequestInterceptor {

  val LOGGER = LoggerFactory.getLogger(classOf[CustomClientHttpRequestInterceptor])

  override def intercept(request: HttpRequest, body: Array[Byte], execution: ClientHttpRequestExecution): ClientHttpResponse = {
    logRequestDetails(request)
    execution.execute(request, body)
  }

  def logRequestDetails(request: HttpRequest): Unit = {
    LOGGER.info("Request Headers: {}", request.getHeaders)
    LOGGER.info("Request Method: {}", request.getMethod)
    LOGGER.info("Request URI: {}", request.getURI)
  }
}
