package com.carhub.api.social.utils.media

import com.carhub.api.social.utils.exception.ServiceUnavailableException
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.context.annotation.{Bean, Configuration}

object MediaUtil {

  @Autowired
  val loadBalancerClient : LoadBalancerClient = null

  @Value("${media.service.name}")
  val mediaServiceName : String = null

  def instance() = this

  @Bean
  def mediaService(): ServiceInstance = {
    val service = loadBalancerClient.choose(mediaServiceName)
    if(service == null)
      throw new ServiceUnavailableException("Media")

    service
  }
}
@Configuration
class MediaUtilConfig {
  @Bean
  def mediaUtil = MediaUtil.instance()
}