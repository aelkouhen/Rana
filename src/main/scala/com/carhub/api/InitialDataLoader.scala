package com.carhub.api

import java.awt.image.BufferedImage
import java.text.SimpleDateFormat
import java.util.{Calendar, Locale, UUID}

import com.carhub.api.social.domain._
import com.carhub.api.social.domain.dto.Photo
import com.carhub.api.social.domain.enumerations._
import com.carhub.api.social.repositories._
import com.carhub.api.social.utils.jwt.JwtUtil
import com.google.common.io.Files
import javax.imageio.ImageIO
import org.springframework.boot.{ApplicationArguments, ApplicationRunner}
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.http.{HttpHeaders, HttpMethod}
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class InitialDataLoader(@Autowired
                                  val channelRepository: ChannelRepository,
                                  val choiceRepository: ChoiceRepository,
                                  val eventMembershipRepository: EventMembershipRepository,
                                  val eventRepository: EventRepository,
                                  val gearheadRepository: GearheadRepository,
                                  val messageRecipientRepository: MessageRecipientRepository,
                                  val messageRepository: MessageRepository,
                                  val photoAlbumRepository: PhotoAlbumRepository,
                                  val pollRepository: PollRepository,
                                  val postRepository: PostRepository,
                                  val relationshipRepository: RelationshipRepository,
                                  val threadRepository: ThreadRepository,
                                  val topicMembershipRepository: TopicMembershipRepository)
                                  extends ApplicationRunner {

  @Autowired
  val loadBalancerClient : LoadBalancerClient = null

  @Value("${media.service.name}")
  val mediaService : String = null

  @Value("${security.oauth2.resource.token-type}")
  val tokenType : String = null

  @Value("${media.photo-endpoint}")
  val photoEndpoint : String = null

  def run(args: ApplicationArguments): Unit = {

    val gearhead = new Gearhead
    gearhead.firstName = "Amine"
    gearhead.lastName = "El Kouhen"
    gearhead.gender = Gender.MALE
    gearhead.username = "aelkouhen"
    gearhead.aboutMe = "lorem ipsum"
    val format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
    val date = format.parse("15/11/1986")
    gearhead.birthDay = date
    gearhead.currentLocation = "36 Rue Saint Henri, 59110 La Madeleine"
    gearhead.coverPhotoId = createPhoto()

    val now = Calendar.getInstance().getTime()
    gearhead.updateTime = now

    gearheadRepository.save(gearhead)

    val channel = new Channel()

    channel.name = "Mustang Lovers"
    channel.creationTime = now
    channel.updateTime = now
    channel.creator = gearhead
    channelRepository.save(channel)

    val poll = new Poll()
    poll.subject = "What to eat this noon ?"
    poll.channel = channel
    poll.creator = gearhead
    poll.creationTime = now
    poll.updateTime = now
    pollRepository.save(poll)

    val choice1 = new Choice()
    choice1.text = "Massala"
    choice1.poll = poll
    choiceRepository.save(choice1)

    val choice2 = new Choice()
    choice2.text = "Factory"
    choice2.poll = poll
    choice2.voters.add(gearhead)
    choiceRepository.save(choice2)

    val choice3 = new Choice()
    choice3.text = "Beer & Co"
    choice3.poll = poll
    choiceRepository.save(choice3)


    val event = new Event()
    event.creator = gearhead
    event.description = "Le plus grand forum automobile au monde"
    event.location = "Porte de Verseille, Paris"
    event.name = "Mondiale d'Automobile"
    event.updateTime = now
    event.creationTime = now
    eventRepository.save(event)

    val eventMembership = new EventMembership
    eventMembership.event = event
    eventMembership.gearhead = gearhead
    eventMembership.status = EventParticipationStatus.ATTENDING
    eventMembership.userType = UserRole.MEMBER
    eventMembershipRepository.save(eventMembership)

    val topicMembership = new TopicMembership
    topicMembership.gearhead = gearhead
    topicMembership.topic = channel
    topicMembership.userType = UserRole.FOLLOWER
    topicMembershipRepository.save(topicMembership)

    val thread = new Thread
    thread.creator = gearhead
    thread.updateTime = now
    thread.creationTime = now
    thread.channel = channel
    thread.subject = "Atelier meca"
    threadRepository.save(thread)

    val topicMembership2 = new TopicMembership
    topicMembership2.gearhead = gearhead
    topicMembership2.topic = thread
    topicMembership2.userType = UserRole.MODERATOR
    topicMembershipRepository.save(topicMembership2)


    val post = new Post
    post.creationTime = now
    post.creator = gearhead
    post.message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam scelerisque nulla lectus, id accumsan felis posuere eu. Etiam commodo mollis volutpat."
    post.subject = "Lorem Ipsum"
    post.thread = thread
    postRepository.save(post)

    val gearhead2 = new Gearhead
    gearhead2.firstName = "Amine"
    gearhead2.lastName = "El Kouhen"
    gearhead2.gender = Gender.MALE
    gearhead2.username = "amineelkouhen"
    gearhead2.aboutMe = "lorem ipsum"
    gearhead2.birthDay = date
    gearhead2.currentLocation = "139 Rue Pompidou, 59110 La Madeleine"
    gearhead2.updateTime = now

    gearheadRepository.save(gearhead2)

    val relationship = new Relationship
    relationship.creationTime = now
    relationship.gearhead = gearhead
    relationship.relatedTo = gearhead2
    relationship.relationshipKind = RelationshipKind.FRIENDSHIP
    relationship.relationshipStatus = RelationshipStatus.CONFIRMED
    relationship.since = format.parse("11/09/2001")
    relationshipRepository.save(relationship)

    val message = new Message
    message.creator = gearhead
    message.creationTime = now
    message.subject = "Welcome message"
    message.body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam scelerisque nulla lectus, id accumsan felis posuere eu. Etiam commodo mollis volutpat"
    messageRepository.save(message)

    val messageRecipient1 = new MessageRecipient
    messageRecipient1.message = message
    messageRecipient1.recipient = gearhead2
    messageRecipient1.status = MessageStatus.RECEIVED
    messageRecipientRepository.save(messageRecipient1)
  }


  private def createPhoto() : UUID = {
    val mediaServiceInstance : ServiceInstance = loadBalancerClient.choose(mediaService)
    if(mediaServiceInstance == null)
      throw new RuntimeException("Media Service is Down")

    val photo = new Photo
    val picture = new ClassPathResource("images/myPic.jpg")
    var inputStream = picture.getInputStream
    val bimg : BufferedImage = ImageIO.read(inputStream)
    inputStream = picture.getInputStream
    photo.width = bimg.getWidth
    photo.height = bimg.getHeight
    val arrayPic = Stream.continually(inputStream.read).takeWhile(-1 !=).map(_.toByte).toArray
    inputStream.close()
    val connection = picture.getURL.openConnection
    photo.mimeType = connection.getContentType
    photo.size = picture.contentLength
    photo.name = Files.getNameWithoutExtension(picture.getFilename)
    photo.format = Files.getFileExtension(picture.getFilename)
    photo.caption = "Amine's Pic"
    photo.created = Calendar.getInstance().getTime()
    photo.content = arrayPic

    val client = WebClient.builder()
                    .baseUrl(mediaServiceInstance.getUri.toString)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, tokenType + " " + JwtUtil.token())
                    .build()

    val request = client
      .method(HttpMethod.POST)
      .uri(photoEndpoint).body(BodyInserters.fromObject(photo))

    val result = request.retrieve()
      .bodyToMono(classOf[Photo])
      .block()

    result.id
  }
}
