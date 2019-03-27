package com.carhub.api.social.utils.http

import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.web.client.RestTemplate

class CustomRestTemplateCustomizer extends RestTemplateCustomizer{
  override def customize(restTemplate: RestTemplate): Unit = restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor())
}
