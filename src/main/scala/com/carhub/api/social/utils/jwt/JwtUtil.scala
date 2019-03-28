package com.carhub.api.social.utils.jwt

import java.lang.Long
import java.util.{Calendar, Date}

import org.apache.commons.codec.binary.Base64
import org.json.JSONObject
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.{HttpHeaders, HttpMethod, MediaType}
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

object JwtUtil {

  @Autowired
  val loadBalancerClient : LoadBalancerClient = null

  @Value("${service.auth.serviceId}")
  val authService : String = null

  @Value("${security.oauth2.resource.client.client-id}")
  val clientId : String = null

  @Value("${security.oauth2.resource.client.client-secret}")
  val clientSecret : String = null

  @Value("${security.oauth2.resource.token-type}")
  val tokenType : String = null

  @Value("${oauth2.token-get-path}")
  val tokenPath : String = null

  @Value("${spring.datasource.username}")
  val username : String = null

  @Value("${spring.datasource.password}")
  val password : String = null

  var accessToken : String = null

  var tokenExpirationDate : Date = null

  def instance() = this

  def token() : String ={
    if(accessToken == null || Calendar.getInstance().getTime().after(tokenExpirationDate)){
      val authServiceInstance : ServiceInstance = loadBalancerClient.choose(authService)
      if(authServiceInstance == null)
        throw new RuntimeException("Authentication Service is Down")

      val client = WebClient.builder()
        .baseUrl(authServiceInstance.getUri.toString)
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + createAuthorization(clientId, clientSecret))
        .build()

      val credentials = new LinkedMultiValueMap[String, String]()
      credentials.add("grant_type", "password")
      credentials.add("username", username)
      credentials.add("password", password)

      val request = client
        .method(HttpMethod.POST)
        .uri(tokenPath)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .accept( MediaType.APPLICATION_JSON )
        .body(BodyInserters.fromFormData(credentials))

      val result = request.retrieve()
        .bodyToMono(classOf[String])
        .block()

      val json = new JSONObject(result)
      accessToken = json.get("access_token").toString
      tokenExpirationDate = new Date(Calendar.getInstance().getTimeInMillis() + Long.parseLong(json.get("expires_in").toString))
    }
    accessToken
  }

  def createAuthorization( username: String,  password:String) : String = {
    val auth = (username ++ ":" ++ password).getBytes("UTF-8")
    Base64.encodeBase64String(auth)
  }
}

@Configuration
class JwtUtilConfig {
  @Bean
  def jwtUtil = JwtUtil.instance()
}