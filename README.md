# Eureka_Spring_Boot

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
  
