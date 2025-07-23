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
