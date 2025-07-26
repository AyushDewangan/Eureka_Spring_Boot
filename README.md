# Eureka_Spring_Boot (Service Registry)

Steps to Run All Micro Services:

1. Firstly Run 'EurekaServer'
2. CitizenService
3. VaccinationCenter

Eureka Port: http://localhost:8761/
CitizenService Port: 8081
VaccinationCenter Port: 8082

Requirements:

MySQL
  Create Databases: CitizenDB, VaccinationCenterDB

Sample API and Payloads:
  Add Citizen: 
    http://localhost:8081/citizen/add
    POST
    {
	    "name": "name7",
      "vaccinationCenterId": 2
    }
  Get Citizen By Vaccination Center Id:
    http://localhost:8081/citizen/vaccinCenterId/1
    GET

  Add Vaccination Center
    http://localhost:8082/vaccinationcenter/add
    POST
    {
	    "centerName": "Center2",
      "centerAddress": "Bhilai"
    }
  Get Vaccination Center by Id
    http://localhost:8082/vaccinationcenter/vaccinationcenterid/2
    GET
    (It Internally Calling Citizen Service using eureka server)
  


# Resiliience

Fault Tolerance : Fault Tolerance is the property that enables a system to continue operating properly in the event of failure of some of its component.

Example: Netflix Hystrix(Deprecated(till springboot2)/Not used in industry because of large memory consumption) or resilience4j

Using circuit breaker design pattern we can implement fault tolerance.

**Circuit Breaker Design Pattern:

1) Idea behind circuit breaker is :
a) Wrap your rest APIs call in circuit breaker object which monitors for failure
b) Once the failures reach a certain threshold, the circuit breaker trips, and all further calls to
the circuit breaker return with an error,
c) Here comes our task: If it fails and circuit is open, configure a fallback method which will
be executed as soon as circuit breaks or opens.

Code:
// Add Dependancy
<dependency>
    	<groupId>io.github.resilience4j</groupId>
	<artifactId>resilience4j-spring-boot3</artifactId>
</dependency>

Property Need to Add:
resilience4j:
  circuitbreaker:
    instances:
      vaccinationCenter:
        registerHealthIndicator: true
        slidingWindowSize: 2
        minimumNumberOfCalls: 2
        failureRateThreshold: 50
        waitDurationInOpenState: 5s

 
Code wise need to make some chnages in the code. (Please check the below files for the resilience code changes)
VaccinationCenter/pom.xml
VaccinationCenter/src/main/java/com/example/VaccinationCenter/Service/VaccinationCenterService.java
VaccinationCenter/src/main/resources/application.yml





# API Gateway:

1. Abstraction Layer Between FE and BE.
2. Why we need API Gateway:
	We will scale up our microservice, client need to now exactly which microservice they need. Client/FE don't need such information that backend developer is developing which microservice for which functionality. To over come this we have API Gateway to create a abstraction layer between FE and BE developers. Thus FE developers need not depends on backend people anymore. This is a good as implementing Abstraction principle in java oops.
3. Now onwards Client /FE need to call only API Gateway it will route the microservices.
4. Its good practice to implement Authentication, Authorization, Logging, Monitoring etc. in the API Gateway it self.

Sample Of API Gateways:
1. Netflix API Gateway (Zuul)
2. Amazon API Gateway
3. MuleSoft
4. Kong API Gateway
5. Azure API Gateway
6. Spring Cloud API Gateway

Spring Cloud Gateway: 

Note: Some of the Netflix products(Hystrix and Zuul) are going to maintenance only that means from now onwards its only fixes the bug and little  bit of improvement but not going to provide new feature that's why some of is products are now going to deprecated. Netflix eureka is in development so we can use it.

Alternate of Hystrix is resilience4j or Atlas.

Zuul: Zuul server is blocking API, That means example if server has 4 thread free and then 10 request is coming to the zuul then it will execute 4 API calls as 4 threads are available but remaining 6 were in queue(Block State).


How to implement Spring Cloud API Gateway:

1. Craete new spring boot project and add dependency like: spring-cloud-starter-gateway and spring-cloud-starter-netflix-eureka-client.

2. application.properties

server:
  port: 8083

spring:
  application:
    name: API_GATEWAY
  cloud:
    gateway:
      routes:
        - id: CITIZEN-SERVICE
          uri: lb://CITIZEN-SERVICE //If you added load balancer
	  uri: http://localhost:8081 //If you are not added the load balancer
          predicates:
            - Path=/citizen/**

        - id: VACCINATION-CENTER-SERVICE
          uri: lb://VACCINATION-CENTER-SERVICE
          predicates:
            - Path=/vaccinationcenter/**


1. To include Spring Cloud Gateway in our project, use the starter with a group ID of org.apringframework.cloud and an artifact id of spring-cloud-starter-gateway.
2. It this api gateway server as eureka client
3. If we include the starter, but now we don't want to enable gateway then set: spring.cloud.gateway.enable: false
4. Predicate: This is a java 8 function predicate. The input type is spring framework  ServerWebExchange. This lets you match on anything from the the HTTP request, such a headers or parameters.
5. Route: This is the basic building block of the gateway. It is defines by an ID, a destination url, a collection of predicates, and a collection of filters. A route is matched if the aggregate predicate is true.
