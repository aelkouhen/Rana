package com.carhub.api

import com.carhub.api.social.utils.exception.ServiceUnavailableException
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableJpaRepositories(basePackages = Array("com.carhub.api.social.repositories"))
class SpringBootConfig{

  @Autowired
  val loadBalancerClient : LoadBalancerClient = null

  @Value("${media.service.name}")
  val mediaServiceName : String = null

  @Bean
  def mediaService(): ServiceInstance ={
    if(loadBalancerClient == null)
      throw new ServiceUnavailableException("Discovery")

    val mediaServiceInstance : ServiceInstance = loadBalancerClient.choose(mediaServiceName)
    if(mediaServiceInstance == null)
      throw new ServiceUnavailableException("Media")

    mediaServiceInstance
  }

}
