package com.carhub.api

import com.carhub.api.social.utils.http.{CustomRestTemplateCustomizer, RestTemplateResponseErrorHandler}
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableJpaRepositories(basePackages = Array("com.carhub.api.social.repositories"))
class SpringBootConfig {
  @Bean
  @Qualifier("customRestTemplateCustomizer")
  def customRestTemplateCustomizer = new CustomRestTemplateCustomizer

  @Bean
  @DependsOn(value = Array(("customRestTemplateCustomizer")))
  def restTemplateBuilder = new RestTemplateBuilder(customRestTemplateCustomizer)

  @Bean
  def restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.errorHandler(new RestTemplateResponseErrorHandler()).build

}
